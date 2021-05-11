import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEndereco, Endereco } from '@/shared/model/endereco.model';
import EnderecoService from './endereco.service';

const validations: any = {
  endereco: {
    endereco: {},
    cep: {},
    cidade: {},
    estado: {},
    coordenadasGps: {},
  },
};

@Component({
  validations,
})
export default class EnderecoUpdate extends Vue {
  @Inject('enderecoService') private enderecoService: () => EnderecoService;
  public endereco: IEndereco = new Endereco();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.enderecoId) {
        vm.retrieveEndereco(to.params.enderecoId);
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
    if (this.endereco.id) {
      this.enderecoService()
        .update(this.endereco)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Endereco is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.enderecoService()
        .create(this.endereco)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Endereco is created with identifier ' + param.id;
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

  public retrieveEndereco(enderecoId): void {
    this.enderecoService()
      .find(enderecoId)
      .then(res => {
        this.endereco = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
