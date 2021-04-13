import { Component, Vue, Inject } from 'vue-property-decorator';

import { IExame, Exame } from '@/shared/model/exame.model';
import ExameService from './exame.service';

const validations: any = {
  exame: {
    nome: {},
    tipo: {},
    resultado: {},
  },
};

@Component({
  validations,
})
export default class ExameUpdate extends Vue {
  @Inject('exameService') private exameService: () => ExameService;
  public exame: IExame = new Exame();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.exameId) {
        vm.retrieveExame(to.params.exameId);
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
    if (this.exame.id) {
      this.exameService()
        .update(this.exame)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Exame is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.exameService()
        .create(this.exame)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Exame is created with identifier ' + param.id;
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

  public retrieveExame(exameId): void {
    this.exameService()
      .find(exameId)
      .then(res => {
        this.exame = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
