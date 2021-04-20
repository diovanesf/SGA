import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IPropriedade } from '@/shared/model/propriedade.model';
import PropriedadeService from './propriedade.service';

@Component
export default class PropriedadeDetails extends mixins(JhiDataUtils) {
  @Inject('propriedadeService') private propriedadeService: () => PropriedadeService;
  public propriedade: IPropriedade = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.propriedadeId) {
        vm.retrievePropriedade(to.params.propriedadeId);
      }
    });
  }

  public retrievePropriedade(propriedadeId) {
    this.propriedadeService()
      .find(propriedadeId)
      .then(res => {
        this.propriedade = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
