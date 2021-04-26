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
    pesoMaterial: {},
    estimativaVacinas: {},
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

  public amostra: IAmostra = {};
  public isSaving = false;
  public currentLanguage = '';
  public tiposVirus: String[] = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.exameId) {
        vm.retrieveExame(to.params.exameId);
      }
      if (to.params.amostraId) {
        vm.retrieveAmostra(to.params.amostraId);
      }
      vm.addTodosTipoVirus();
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
          this.$router.push({ name: 'Exame' })
          // this.$router.go(-1);
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
          // this.$router.go(-1);
          this.$router.push({ name: 'Exame' })
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
    this.$router.push({ name: 'Exame' });
  }

  public retrieveAmostra(amostraId: number): void {
    this.amostraService()
      .find(amostraId)
      .then(res => {
        this.amostra = res;
        this.exame.amostra = res;
      });
  }

  public addTodosTipoVirus() {
    this.addBvdvTipoVirus();
    this.addIbrTipoVirus();
    this.addEhvTipoVirus();
    this.addLebTipoVirus();
    this.addCdvTipoVirus();
    this.addEhvTipoVirus();
    this.addAieTipoVirus();
    this.addFcmTipoVirus();
    this.addBohvTipoVirus();
    this.addOrfvTipoVirus();
    this.addEavTipoVirus();
    this.addFivFelvTipoVirus();
    this.addRabvTipoVirus();
    this.addInfluenzaEquinaTipoVirus();
    this.addCpvTipoVirus();
    this.addBrsvTipoVirus();
    this.addCoronavirusTipoVirus();
    this.addRotavirusTipoVirus();
  }

  public filtraTipoVirusPorTipoExame() {
    let virus = "";
    switch (this.exame.nome) {
      case 'SORONEUTRALIZACAO':
        this.tiposVirus = [];
        this.addBvdvTipoVirus();
        this.addIbrTipoVirus();
        this.addEhvTipoVirus();
        this.addEavTipoVirus();
        break;
      case 'ENSAIO_IMUNOABSORCAO_ENZIMATICA':
        this.tiposVirus = [];
        this.addBvdvTipoVirus();
        this.addIbrTipoVirus();
        this.addLebTipoVirus();
        break;
      case 'REACAO_CADEIA_POLIMERASE':
        this.tiposVirus = [];
        this.addBvdvTipoVirus();
        this.addIbrTipoVirus();
        this.addCdvTipoVirus();
        this.addEhvTipoVirus();
        this.addAieTipoVirus();
        this.addFcmTipoVirus();
        this.addBohvTipoVirus();
        this.addOrfvTipoVirus();
        break;
      case 'IMUNOCROMATOGRAFIA':
        this.tiposVirus = [];
        this.addCdvTipoVirus();
        this.addFivFelvTipoVirus();
        break;
      case 'IMUNOFLUORESCENCIA':
        this.tiposVirus = [];
        this.addRabvTipoVirus();
        break;
      case 'INIBICAO_HEMAGLUTINACAO':
        this.tiposVirus = [];
        this.addInfluenzaEquinaTipoVirus();
        break;
      case 'ISOLAMENTO_VIRAL':
        this.tiposVirus = [];
        this.addBvdvTipoVirus();
        this.addIbrTipoVirus();
        this.addCpvTipoVirus();
        this.addBrsvTipoVirus();
        break;
      case 'IMUNODIFUSAO_GEL_AGAR':
        this.tiposVirus = [];
        this.addLebTipoVirus();
        break;
      case 'MICROSCOPIA_ELETRONICA':
        this.tiposVirus = [];
        this.addCoronavirusTipoVirus();
        this.addRotavirusTipoVirus();
        break;
      case 'VACINA_AUTOGENA':
        this.exame.nome = 'VACINA_AUTOGENA';
        break;
    }
  }

  public addBvdvTipoVirus() {
    let bvdv = "BVDV";
    this.tiposVirus.push(bvdv);
  }

  public addIbrTipoVirus() {
    let ibr = "IBR";
    this.tiposVirus.push(ibr);
  }

  public addEhvTipoVirus() {
    let ehv = "EHV";
    this.tiposVirus.push(ehv);
  }

  public addLebTipoVirus() {
    let leb = "LEB";
    this.tiposVirus.push(leb);
  }

  public addCdvTipoVirus() {
    let cdv = "CDV";
    this.tiposVirus.push(cdv);
  }

  public addAieTipoVirus() {
    let aie = "AIE";
    this.tiposVirus.push(aie);
  }

  public addFcmTipoVirus() {
    let fcm = "FCM";
    this.tiposVirus.push(fcm);
  }

  public addBohvTipoVirus() {
    let bohv = "BOHV_5";
    this.tiposVirus.push(bohv);
  }

  public addOrfvTipoVirus() {
    let orfv = "ORFV";
    this.tiposVirus.push(orfv);
  }

  public addEavTipoVirus() {
    let eav = "EAV";
    this.tiposVirus.push(eav);
  }

  public addFivFelvTipoVirus() {
    let fivFelv = "FIV_FELV";
    this.tiposVirus.push(fivFelv);
  }

  public addRabvTipoVirus() {
    let rabv = "RABV";
    this.tiposVirus.push(rabv);
  }

  public addInfluenzaEquinaTipoVirus() {
    let influenzaEquina = "INFLUENZA_EQUINA";
    this.tiposVirus.push(influenzaEquina);
  }

  public addCpvTipoVirus() {
    let cpv = "CPV";
    this.tiposVirus.push(cpv);
  }

  public addBrsvTipoVirus() {
    let brsv = "BRSV";
    this.tiposVirus.push(brsv);
  }

  public addCoronavirusTipoVirus() {
    let coronavirus = "CORONAVIRUS";
    this.tiposVirus.push(coronavirus);
  }

  public addRotavirusTipoVirus() {
    let rotavirus = "ROTAVIRUS";
    this.tiposVirus.push(rotavirus);
  }

}
