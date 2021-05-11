import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMidia } from '@/shared/model/midia.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import MidiaService from './midia.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Midia extends mixins(JhiDataUtils) {
  @Inject('midiaService') private midiaService: () => MidiaService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public midias: IMidia[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMidias();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllMidias();
  }

  public verificaUsuario(): boolean {
    return this.$store.getters.account.authorities.find(elen => elen === 'ROLE_PROFESSOR');
  }

  public retrieveAllMidias(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.midiaService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.midias = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
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

  public prepareRemove(instance: IMidia): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMidia(): void {
    this.midiaService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Midia is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllMidias();
        this.closeDialog();
      });
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'desc' : 'asc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllMidias();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
