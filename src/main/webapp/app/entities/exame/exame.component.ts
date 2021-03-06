import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IExame } from '@/shared/model/exame.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ExameService from './exame.service';
import AmostraService from '../amostra/amostra.service';
import { IAmostra } from '@/shared/model/amostra.model';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Exame extends mixins(JhiDataUtils) {
  @Inject('exameService') private exameService: () => ExameService;
  private removeId: number = null;

  @Inject('amostraService') private amostraService: () => AmostraService;
  public amostra: IAmostra = {};
  private amostraId: number = null;
  public exames: IExame[] = [];

  public isFetching = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.amostraId) {
        vm.amostraId = to.params.amostraId;
        vm.retrieveAmostra(vm.amostraId);
        vm.retrieveExamesByAmostra(vm.amostraId);
      }
    });
  }

  public retrieveAmostra(amostraId): void {
    this.amostraService()
      .find(amostraId)
      .then(res => {
        this.amostra = res;
      });
  }

  public clear(): void {
    this.retrieveExamesByAmostra(this.amostraId);
  }

  public retrieveExamesByAmostra(amostraId: number): void {
    this.isFetching = true;
    this.exameService()
      .retrieveByAmostra(amostraId)
      .then(
        res => {
          this.exames = res.data;
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

  public verificaUsuario(): boolean {
    return this.$store.getters.account.authorities.find(elen => elen === 'ROLE_PROFESSOR');
  }

  public prepareRemove(instance: IExame): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeExame(): void {
    this.exameService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Exame is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveExamesByAmostra(this.amostraId);
        this.closeDialog();
      });
  }

  public transition(): void {
    this.retrieveExamesByAmostra(this.amostraId);
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
  public previousState(): void{
    this.$router.push('/Amostra');
  }
}
