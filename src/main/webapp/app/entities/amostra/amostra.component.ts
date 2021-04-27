import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAmostra } from '@/shared/model/amostra.model';
import { IExame } from '@/shared/model/exame.model';
import ExameService from '@/entities/exame/exame.service';

import AmostraService from './amostra.service';
import { Authority } from '@/shared/security/authority';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Amostra extends Vue {
  @Inject('exameService') private exameService: () => ExameService;
  public exames: IExame[] = [];
  @Inject('amostraService') private amostraService: () => AmostraService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public amostras: IAmostra[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAmostras();
    this.retrieveExames();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllAmostras();
  }

  public retrieveAllAmostras(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.amostraService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.amostras = res.data;
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

  public prepareRemove(instance: IAmostra): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public verificaUsuario(): boolean {
    // console.log(this.$store.getters.account);
    return this.$store.getters.account.authorities.find(elen => elen === 'ROLE_PROFESSOR');
  }

  public removeAmostra(): void {
    this.amostraService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Amostra is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAmostras();
        this.closeDialog();
      });
  }

  // return !amostra.exames.find(elen => elen.resultado === null );
  public verificaExames(amostra: IAmostra): boolean {
    this.retrieveExamesByAmostra(amostra);
    // console.log(amostra.exames);
    let bool = true;
    if (amostra.exames.length > 0) {
      amostra.exames.forEach(function (item, indice, array) {
        if (item.resultado === null) {
          bool = false;
        }
      });
    } else {
      bool = false;
    }
    return bool;
  }

  public retrieveExamesByAmostra(amostra: IAmostra): void {
    this.exames.forEach(function (item, indice, array) {
      // console.log(item);
      amostra.exames = [];
      if (item.amostra !== null) {
        if (item.amostra.id === amostra.id) {
          amostra.exames.push(item);
        }
      }
    });
  }

  public retrieveExames(): void {
    this.isFetching = true;
    this.exameService()
      .retrieve()
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
    this.retrieveAllAmostras();
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
