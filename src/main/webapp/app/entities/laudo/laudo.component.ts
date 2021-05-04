import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAmostra } from '@/shared/model/amostra.model';

import AmostraService from '@/entities/amostra/amostra.service';
import ExameService from '@/entities/exame/exame.service';
import PropriedadeService from '@/entities/propriedade/propriedade.service';
import ProprietarioService from '@/entities/proprietario/proprietario.service';
import { IExame } from '@/shared/model/exame.model';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Laudo extends Vue {
  @Inject('amostraService') private amostraService: () => AmostraService;

  @Inject('exameService') private exameService: () => ExameService;

  @Inject('propriedadeService') private propriedadeService: () => PropriedadeService;

  @Inject('proprietarioService') private proprietarioService: () => ProprietarioService;

  public amostra: IAmostra = {};
  public exames: IExame[] = [];
  public enderecoId: number = null;
  public amostraId: number = null;
  public isFetching = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.amostraId) {
        vm.amostraId = to.params.amostraId;
        vm.retrieveAmostra(to.params.amostraId);
        vm.retrieveExameByAmostra(to.params.amostraId);
      }
    });
  }

  public retrieveAmostra(amostraId: number): void {
    this.isFetching = true;
    this.amostraService()
      .find(amostraId)
      .then(res => {
        this.amostra = res;
        // console.log(this.amostra);
        this.retrievePropriedade(this.amostra.propriedade.id);
        this.isFetching = false;
      });
  }

  public retrieveExameByAmostra(amostraId: number): void {
    this.exameService()
      .retrieveByAmostra(amostraId)
      .then(res => {
        this.exames = res.data;
      });
  }

  public retrievePropriedade(propriedadeId: number): void {
    this.propriedadeService()
      .find(propriedadeId)
      .then(res => {
        this.amostra.propriedade = res;
        console.log(this.amostra.propriedade);
        this.retrieveProprietario(this.amostra.propriedade.proprietario.id);
      });
  }

  public retrieveProprietario(proprietarioId: number): void {
    this.proprietarioService()
      .find(proprietarioId)
      .then(res => {
        this.amostra.propriedade.proprietario = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }
}
