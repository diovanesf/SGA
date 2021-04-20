import { Component, Vue, Inject } from 'vue-property-decorator';

import EnderecoService from '@/entities/endereco/endereco.service';
import { IEndereco } from '@/shared/model/endereco.model';

import PropriedadeService from '@/entities/propriedade/propriedade.service';
import { IPropriedade } from '@/shared/model/propriedade.model';

import { IProprietario, Proprietario } from '@/shared/model/proprietario.model';
import ProprietarioService from './proprietario.service';

const validations: any = {
  proprietario: {
    nome: {},
    telefone: {},
    email: {},
    enviarLaudo: {},
  },
};

@Component({
  validations,
})
export default class ProprietarioUpdate extends Vue {
  @Inject('proprietarioService') private proprietarioService: () => ProprietarioService;
  public proprietario: IProprietario = new Proprietario();

  @Inject('enderecoService') private enderecoService: () => EnderecoService;

  public enderecos: IEndereco[] = [];

  @Inject('propriedadeService') private propriedadeService: () => PropriedadeService;

  public propriedades: IPropriedade[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proprietarioId) {
        vm.retrieveProprietario(to.params.proprietarioId);
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
    if (this.proprietario.id) {
      this.proprietarioService()
        .update(this.proprietario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Proprietario is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.proprietarioService()
        .create(this.proprietario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Proprietario is created with identifier ' + param.id;
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

  public retrieveProprietario(proprietarioId): void {
    this.proprietarioService()
      .find(proprietarioId)
      .then(res => {
        this.proprietario = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.enderecoService()
      .retrieve()
      .then(res => {
        this.enderecos = res.data;
      });
    this.propriedadeService()
      .retrieve()
      .then(res => {
        this.propriedades = res.data;
      });
  }
}
