import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAmostra } from '@/shared/model/amostra.model';
import AmostraService from './amostra.service';
import EnderecoService from '@/entities/endereco/endereco.service';
import PropriedadeService from '@/entities/propriedade/propriedade.service';

@Component
export default class AmostraDetails extends Vue {
  @Inject('amostraService') private amostraService: () => AmostraService;
  public amostra: IAmostra = {};
  @Inject('enderecoService') private enderecoService: () => EnderecoService;
  @Inject('propriedadeService') private propriedadeService: () => PropriedadeService;
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.amostraId) {
        vm.retrieveAmostra(to.params.amostraId);
      }
    });
  }

  public retrieveAmostra(amostraId) {
    this.amostraService()
      .find(amostraId)
      .then(res => {
        this.amostra = res;
        console.log(this.amostra);
        this.retrievePropriedade(this.amostra.propriedade.id);
      });
  };

  public retrievePropriedade(propriedadeId: number) {
    this.propriedadeService()
      .find(propriedadeId)
      .then(res => {
        this.amostra.propriedade = res;
        console.log(this.amostra);
        this.retrieveEndereco(this.amostra.propriedade.endereco.id);
      });
  };

  public retrieveEndereco(enderecoId: number) {
    this.enderecoService()
      .find(enderecoId)
      .then(res => {
        this.amostra.propriedade.endereco = res;
        console.log(this.amostra);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
