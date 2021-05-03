import { Component, Vue, Inject } from 'vue-property-decorator';

import PropriedadeService from '@/entities/propriedade/propriedade.service';
import { IPropriedade } from '@/shared/model/propriedade.model';

import MedicoveterinarioService from '@/entities/medicoveterinario/medicoveterinario.service';
import { IMedicoveterinario } from '@/shared/model/medicoveterinario.model';

import { IAmostra, Amostra } from '@/shared/model/amostra.model';
import AmostraService from './amostra.service';

const validations: any = {
  amostra: {
    protocolo: {},
    formaEnvio: {},
    numeroAmostras: {},
    especie: {},
    dataInicial: {},
    dataFinal: {},
    materialRecebido: {},
    acondicionamento: {},
    condicaoMaterial: {},
    numeroAnimais: {},
    acometidos: {},
    pricipalSuspeita: {},
    tipo: {},
    status: {},
    tipoMedVet: {},
    valorTotal: {},
    tipoPagamento: {},
    situacao: {},
  },
};

@Component({
  validations,
})
export default class AmostraUpdate extends Vue {
  @Inject('amostraService') private amostraService: () => AmostraService;
  public amostra: IAmostra = new Amostra();

  @Inject('propriedadeService') private propriedadeService: () => PropriedadeService;

  public propriedades: IPropriedade[] = [];

  @Inject('medicoveterinarioService') private medicoveterinarioService: () => MedicoveterinarioService;

  public medicoveterinarios: IMedicoveterinario[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.amostraId) {
        vm.retrieveAmostra(to.params.amostraId);
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
      this.setUser();
      this.setDataInicial();
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

  setMedVet() {
    switch (this.amostra.tipoMedVet) {
      case 'SEM_MED_VET':
      case 'MESMO_PROPRIETARIO':
        this.amostra.medicoveterinario = null;
        break;
    }
  }

  setDataInicial() {
    let dataInicial = new Date();
    this.amostra.dataInicial = dataInicial;
  }

  // setDataFinal(){
  //   let dataFinal = new Date();
  //   dataFinal.setDate(dataFinal.getDate() + 15);
  //   this.amostra.dataFinal = dataFinal;
  // }

  public setUser() {
    this.amostra.users = [];
    console.log(this.amostra.users);
    this.amostra.users.push(this.$store.getters.account);
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

  public initRelationships(): void {
    this.propriedadeService()
      .retrieve()
      .then(res => {
        this.propriedades = res.data;
      });
    this.medicoveterinarioService()
      .retrieve()
      .then(res => {
        this.medicoveterinarios = res.data;
      });
  }
}
