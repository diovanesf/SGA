import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMedicoveterinario } from '@/shared/model/medicoveterinario.model';

import MedicoveterinarioService from './medicoveterinario.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Medicoveterinario extends Vue {
  @Inject('medicoveterinarioService') private medicoveterinarioService: () => MedicoveterinarioService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public medicoveterinarios: IMedicoveterinario[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMedicoveterinarios();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllMedicoveterinarios();
  }

  public retrieveAllMedicoveterinarios(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.medicoveterinarioService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.medicoveterinarios = res.data;
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

  public prepareRemove(instance: IMedicoveterinario): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMedicoveterinario(): void {
    this.medicoveterinarioService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Medicoveterinario is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllMedicoveterinarios();
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
    this.retrieveAllMedicoveterinarios();
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
