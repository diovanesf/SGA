import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IMidia } from '@/shared/model/midia.model';
import MidiaService from './midia.service';

@Component
export default class MidiaDetails extends mixins(JhiDataUtils) {
  @Inject('midiaService') private midiaService: () => MidiaService;
  public midia: IMidia = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.midiaId) {
        vm.retrieveMidia(to.params.midiaId);
      }
    });
  }

  public retrieveMidia(midiaId) {
    this.midiaService()
      .find(midiaId)
      .then(res => {
        this.midia = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
