import { Component, Vue, Inject } from 'vue-property-decorator';

import ProprietarioService from '@/entities/proprietario/proprietario.service';
import { IProprietario } from '@/shared/model/proprietario.model';

import EnderecoService from '@/entities/endereco/endereco.service';

import { IPropriedade, Propriedade } from '@/shared/model/propriedade.model';
import PropriedadeService from './propriedade.service';

const validations: any = {
  propriedade: {
    tipoPropriedade: {},
    tipoCriacao: {},
  },
};

@Component({
  validations,
})
export default class PropriedadeUpdate extends Vue {
  @Inject('propriedadeService') private propriedadeService: () => PropriedadeService;
  public propriedade: IPropriedade = new Propriedade();

  @Inject('proprietarioService') private proprietarioService: () => ProprietarioService;

  public proprietarios: IProprietario[] = [];

  @Inject('enderecoService') private enderecoService: () => EnderecoService;

  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.propriedadeId) {
        vm.retrievePropriedade(to.params.propriedadeId);
      }
      if (to.params.enderecoId) {
        vm.retrieveEnderecoPropriedade(to.params.enderecoId);
      }else {
        vm.propriedade.endereco = {};
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

  setTipoPropriedade() {
    switch (this.propriedade.tipoPropriedade) {
      case 'CANIL_GATIL':
        this.propriedade.tipoCriacao = null;
        break;
    }
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

  public retrieveEnderecoPropriedade(enderecoId: number): void {
    this.enderecoService()
      .find(enderecoId)
      .then(res => {
        this.propriedade.endereco = res;
      });
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
    this.proprietarioService()
      .retrieve()
      .then(res => {
        this.proprietarios = res.data;
      });
  }
}
