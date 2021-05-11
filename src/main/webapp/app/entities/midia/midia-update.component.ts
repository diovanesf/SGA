import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import AmostraService from '@/entities/amostra/amostra.service';
import { IAmostra } from '@/shared/model/amostra.model';

import { IMidia, Midia } from '@/shared/model/midia.model';
import MidiaService from './midia.service';

const validations: any = {
  midia: {
    nome: {},
    descricao: {},
    file: {},
  },
};

@Component({
  validations,
})
export default class MidiaUpdate extends mixins(JhiDataUtils) {
  @Inject('midiaService') private midiaService: () => MidiaService;
  public midia: IMidia = new Midia();

  @Inject('amostraService') private amostraService: () => AmostraService;

  public amostras: IAmostra[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.midiaId) {
        vm.retrieveMidia(to.params.midiaId);
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
    if (this.midia.id) {
      this.midiaService()
        .update(this.midia)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Midia is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.midiaService()
        .create(this.midia)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Midia is created with identifier ' + param.id;
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

  public retrieveMidia(midiaId): void {
    this.midiaService()
      .find(midiaId)
      .then(res => {
        this.midia = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.amostraService()
      .retrieve()
      .then(res => {
        this.amostras = res.data;
      });
  }
}
