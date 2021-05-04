import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IVacina, Vacina } from '@/shared/model/vacina.model';
import VacinaService from './vacina.service';

const validations: any = {
  vacina: {
    status: {},
    pesoMaterial: {},
    estimativaVacinas: {},
    dataConclusao: {},
    observacoes: {},
  },
};

@Component({
  validations,
})
export default class VacinaUpdate extends mixins(JhiDataUtils) {
  @Inject('vacinaService') private vacinaService: () => VacinaService;
  public vacina: IVacina = new Vacina();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.vacinaId) {
        vm.retrieveVacina(to.params.vacinaId);
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
    if (this.vacina.id) {
      this.vacinaService()
        .update(this.vacina)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Vacina is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.vacinaService()
        .create(this.vacina)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Vacina is created with identifier ' + param.id;
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

  public retrieveVacina(vacinaId): void {
    this.vacinaService()
      .find(vacinaId)
      .then(res => {
        this.vacina = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
