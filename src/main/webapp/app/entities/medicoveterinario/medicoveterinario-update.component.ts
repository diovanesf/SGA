import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMedicoveterinario, Medicoveterinario } from '@/shared/model/medicoveterinario.model';
import MedicoveterinarioService from './medicoveterinario.service';

const validations: any = {
  medicoveterinario: {
    nome: {},
    telefone: {},
    email: {},
    crmv: {},
    enviarLaudo: {},
  },
};

@Component({
  validations,
})
export default class MedicoveterinarioUpdate extends Vue {
  @Inject('medicoveterinarioService') private medicoveterinarioService: () => MedicoveterinarioService;
  public medicoveterinario: IMedicoveterinario = new Medicoveterinario();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.medicoveterinarioId) {
        vm.retrieveMedicoveterinario(to.params.medicoveterinarioId);
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
    if (this.medicoveterinario.id) {
      this.medicoveterinarioService()
        .update(this.medicoveterinario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Medicoveterinario is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.medicoveterinarioService()
        .create(this.medicoveterinario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Medicoveterinario is created with identifier ' + param.id;
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

  public retrieveMedicoveterinario(medicoveterinarioId): void {
    this.medicoveterinarioService()
      .find(medicoveterinarioId)
      .then(res => {
        this.medicoveterinario = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
