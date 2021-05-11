import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IExame } from '@/shared/model/exame.model';
import ExameService from './exame.service';

@Component
export default class ExameDetails extends mixins(JhiDataUtils) {
  @Inject('exameService') private exameService: () => ExameService;
  public exame: IExame = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.exameId) {
        vm.retrieveExame(to.params.exameId);
      }
    });
  }

  public retrieveExame(exameId) {
    this.exameService()
      .find(exameId)
      .then(res => {
        this.exame = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
