import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEndereco } from '@/shared/model/endereco.model';

import EnderecoService from './endereco.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Endereco extends Vue {
  @Inject('enderecoService') private enderecoService: () => EnderecoService;
  private removeId: number = null;

  public enderecos: IEndereco[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllEnderecos();
  }

  public clear(): void {
    this.retrieveAllEnderecos();
  }

  public retrieveAllEnderecos(): void {
    this.isFetching = true;

    this.enderecoService()
      .retrieve()
      .then(
        res => {
          this.enderecos = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IEndereco): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeEndereco(): void {
    this.enderecoService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Endereco is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllEnderecos();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
