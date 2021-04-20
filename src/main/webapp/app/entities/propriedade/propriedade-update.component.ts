import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import EnderecoService from '@/entities/endereco/endereco.service';
import { IEndereco } from '@/shared/model/endereco.model';

import { IPropriedade, Propriedade } from '@/shared/model/propriedade.model';
import PropriedadeService from './propriedade.service';

const validations: any = {
  propriedade: {
    tipoPropriedade: {},
    numeroAnimais: {},
    acometidos: {},
    observacoes: {},
    pricipalSuspeita: {},
    tipoCriacao: {},
  },
};

@Component({
  validations,
})
export default class PropriedadeUpdate extends mixins(JhiDataUtils) {
  @Inject('propriedadeService') private propriedadeService: () => PropriedadeService;
  public propriedade: IPropriedade = new Propriedade();

  @Inject('enderecoService') private enderecoService: () => EnderecoService;

  public enderecos: IEndereco[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.propriedadeId) {
        vm.retrievePropriedade(to.params.propriedadeId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.propriedade.id) {
      this.propriedadeService()
        .update(this.propriedade)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Propriedade is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.propriedadeService()
        .create(this.propriedade)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Propriedade is created with identifier ' + param.id;
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrievePropriedade(propriedadeId): void {
    this.propriedadeService()
      .find(propriedadeId)
      .then(res => {
        this.propriedade = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.enderecoService()
      .retrieve()
      .then(res => {
        this.enderecos = res.data;
      });
  }
}
