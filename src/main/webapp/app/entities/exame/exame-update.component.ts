import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import AmostraService from '@/entities/amostra/amostra.service';
import { IAmostra } from '@/shared/model/amostra.model';

import { IExame, Exame } from '@/shared/model/exame.model';
import ExameService from './exame.service';

const validations: any = {
  exame: {
    nome: {},
    tipo: {},
    resultado: {},
    dataTeste: {},
    dataLeitura: {},
    preenchimentoEspelho: {},
    observacoes: {},
    valor: {},
  },
};

@Component({
  validations,
})
export default class ExameUpdate extends mixins(JhiDataUtils) {
  @Inject('exameService') private exameService: () => ExameService;
  public exame: IExame = new Exame();

  @Inject('amostraService') private amostraService: () => AmostraService;

  public amostras: IAmostra[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.exameId) {
        vm.retrieveExame(to.params.exameId);
      }
      if (to.params.amostraId) {
        vm.retrieveAmostra(to.params.amostraId);
      }
      // vm.initRelationships();
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

  // public initRelationships(): void {
    public retrieveAmostra(amostraId: number): void {
    this.amostraService()
      .find(amostraId)
      .then(res => {
        // this.amostras = res.data;
          this.exame.amostra = res;
      });
  }
}
