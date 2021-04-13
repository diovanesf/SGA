import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAmostra, Amostra } from '@/shared/model/amostra.model';
import AmostraService from './amostra.service';

const validations: any = {
  amostra: {
    protocolo: {},
    formaEnvio: {},
    numeroAmostras: {},
    especie: {},
    materialRecebido: {},
    acondicionamento: {},
    condicaoMaterial: {},
    status: {},
  },
};

@Component({
  validations,
})
export default class AmostraUpdate extends Vue {
  @Inject('amostraService') private amostraService: () => AmostraService;
  public amostra: IAmostra = new Amostra();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.amostraId) {
        vm.retrieveAmostra(to.params.amostraId);
      }
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
    if (this.amostra.id) {
      this.amostraService()
        .update(this.amostra)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Amostra is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.amostraService()
        .create(this.amostra)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Amostra is created with identifier ' + param.id;
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

  public retrieveAmostra(amostraId): void {
    this.amostraService()
      .find(amostraId)
      .then(res => {
        this.amostra = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
