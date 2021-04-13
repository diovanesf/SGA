import { Component, Vue, Inject } from 'vue-property-decorator';

import UserService from '@/admin/user-management/user-management.service';

import MidiaService from '@/entities/midia/midia.service';
import { IMidia } from '@/shared/model/midia.model';

import ExameService from '@/entities/exame/exame.service';
import { IExame } from '@/shared/model/exame.model';

import ProprietarioService from '@/entities/proprietario/proprietario.service';
import { IProprietario } from '@/shared/model/proprietario.model';

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

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('midiaService') private midiaService: () => MidiaService;

  public midias: IMidia[] = [];

  @Inject('exameService') private exameService: () => ExameService;

  public exames: IExame[] = [];

  @Inject('proprietarioService') private proprietarioService: () => ProprietarioService;

  public proprietarios: IProprietario[] = [];
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
    this.amostra.users = [];
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

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.midiaService()
      .retrieve()
      .then(res => {
        this.midias = res.data;
      });
    this.exameService()
      .retrieve()
      .then(res => {
        this.exames = res.data;
      });
    this.proprietarioService()
      .retrieve()
      .then(res => {
        this.proprietarios = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}