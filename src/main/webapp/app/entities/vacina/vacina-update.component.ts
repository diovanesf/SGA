import {Component, Inject} from 'vue-property-decorator';

import {mixins} from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import AmostraService from '@/entities/amostra/amostra.service';
import {IAmostra, Amostra} from '@/shared/model/amostra.model';

import {IVacina, Vacina} from '@/shared/model/vacina.model';
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
  @Inject('amostraService') private amostraService: () => AmostraService;
  public amostra: IAmostra = new Amostra();
  public vacina: IVacina = new Vacina();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.amostraId) {
        console.log(to.params.amostraId);
        vm.retrieveAmostra(to.params.amostraId);
        console.log(vm.amostra);
      }
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
    this.amostra.vacina = this.vacina;
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
  }

  // public save(): void {
  //   this.isSaving = true;
  //   if (this.vacina.id) {
  //     this.vacinaService()
  //       .update(this.vacina)
  //       .then(param => {
  //         this.isSaving = false;
  //         this.$router.go(-1);
  //         const message = 'A Vacina is updated with identifier ' + param.id;
  //         return this.$root.$bvToast.toast(message.toString(), {
  //           toaster: 'b-toaster-top-center',
  //           title: 'Info',
  //           variant: 'info',
  //           solid: true,
  //           autoHideDelay: 5000,
  //         });
  //       });
  //   } else {
  //     this.vacinaService()
  //       .create(this.vacina)
  //       .then(param => {
  //         this.isSaving = false;
  //         this.$router.go(-1);
  //         const message = 'A Vacina is created with identifier ' + param.id;
  //         this.$root.$bvToast.toast(message.toString(), {
  //           toaster: 'b-toaster-top-center',
  //           title: 'Success',
  //           variant: 'success',
  //           solid: true,
  //           autoHideDelay: 5000,
  //         });
  //       });
  //   }
  // }

  public retrieveVacina(vacinaId: number): void {
    this.vacinaService()
      .find(vacinaId)
      .then(res => {
        this.vacina = res;
      });
  }

  public setDataConclusao(): void {
    let dataConclusao = new Date(this.amostra.dataInicial);
    dataConclusao.setDate(dataConclusao.getDate() + 15);
    this.vacina.dataConclusao = dataConclusao;
  }

  // setDataFinal(){
  //   let dataFinal = new Date();
  //   dataFinal.setDate(dataFinal.getDate() + 15);
  //   this.amostra.dataFinal = dataFinal;
  // }

  // public retrieveAmostra(amostraId: number): void {
  //   this.amostraService()
  //     .find(amostraId)
  //     .then(res => {
  //       this.amostra = res;
  //       console.log(this.amostra);
  //     });
  // }

  public retrieveAmostra(amostraId: number): void {
    this.amostraService()
      .find(amostraId)
      .then(res => {
        this.amostra = res;
        if (this.amostra.vacina === null){
          this.setDataConclusao();
        }
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

}
