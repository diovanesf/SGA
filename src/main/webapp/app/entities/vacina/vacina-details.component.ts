import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IVacina } from '@/shared/model/vacina.model';
import VacinaService from './vacina.service';

@Component
export default class VacinaDetails extends mixins(JhiDataUtils) {
  @Inject('vacinaService') private vacinaService: () => VacinaService;
  public vacina: IVacina = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.vacinaId) {
        vm.retrieveVacina(to.params.vacinaId);
      }
    });
  }

  public retrieveVacina(vacinaId) {
    this.vacinaService()
      .find(vacinaId)
      .then(res => {
        this.vacina = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
