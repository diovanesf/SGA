import {mixins} from 'vue-class-component';

import {Component, Vue, Inject} from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import {IAmostra} from '@/shared/model/amostra.model';

import AmostraService from '@/entities/amostra/amostra.service';
import ExameService from '@/entities/exame/exame.service';
import PropriedadeService from '@/entities/propriedade/propriedade.service';
import ProprietarioService from '@/entities/proprietario/proprietario.service';
import {IExame} from '@/shared/model/exame.model';
import jsPDF from 'jspdf';

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
  public cont: number = 180;

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

  public downloadPDF(): void {
    const doc = new jsPDF();
    // doc.setLineWidth(20);
    // doc.lines([[20,20],[-2,2],[1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9],[2,1]], 212,110, 20);
    doc.setFont("times", "normal");
    doc.setFontSize(20);
    doc.text('Laudo de Exame Virológico '+ this.amostra.protocolo, 105, 10, null, null, "center");
    doc.setFontSize(16);
    doc.text('Dados do cliente:  '+ this.amostra.propriedade.proprietario.nome,10,30);
    doc.setFontSize(16);
    doc.text('Endereço: '+ this.amostra.propriedade.endereco.endereco,15,40);
    doc.setFontSize(16);
    doc.text('Cidade: '+ this.amostra.propriedade.endereco.cidade,15,50);
    doc.setFontSize(16);
    doc.text('Email: '+ this.amostra.propriedade.proprietario.email,15,60);
    doc.setFontSize(16);
    doc.text('Fone: '+ this.amostra.propriedade.proprietario.telefone,15,70);
    doc.setFontSize(20);
    doc.text('Identificação da Amostra',10,85);
    doc.setFontSize(16);
    doc.text('Material Recebido: '+ this.amostra.materialRecebido,15,95);
    doc.setFontSize(16);
    doc.text('Espécie: '+ this.amostra.especie,15,105);
    doc.setFontSize(16);
    doc.text('Acondicionamento: '+ this.amostra.acondicionamento,15,115);
    doc.setFontSize(16);
    doc.text('Condição do Material: '+ this.amostra.condicaoMaterial,15,125);
    doc.setFontSize(16);
    doc.text('Data: '+ this.amostra.dataInicial,15,135);
    doc.setFontSize(20);
    doc.text('Exames e seus resultados',10,150);

    // var headers = createHeaders([
    //   "Nome",
    //   "Tipo",
    //   "Resultado",
    //   "Amostra"
    // ]);
    //
    // var generateData = function(exames) {
    //   var result = [];
    //   exames.forEach(value => {
    //   var data = {
    //     Nome: value.nome,
    //     Tipo: value.tipo,
    //     Resultado: value.resultado,
    //     Amostra: value.subamostra.subAmostra
    //   };
    //   result.push(Object.assign({}, data));
    //   console.log("Aqui");
    //   });
    //   return result;
    // };
    //
    // function createHeaders(keys) {
    //   var result = [];
    //   for (var i = 0; i < keys.length; i += 1) {
    //     result.push({
    //       id: keys[i],
    //       name: keys[i],
    //       prompt: keys[i],
    //       width: 65,
    //       align: "center",
    //       padding: 0
    //     });
    //   }
    //   return result;
    // }
    //
    // doc.table(20, 180, generateData(this.amostra.exames), headers, { autoSize: true });

    // var doc = new jsPDF({ putOnlyUsedFonts: true, orientation: "landscape" });

    doc.setFontSize(18);
    doc.text('Nome', 20, 165);
    doc.setFontSize(18);
    doc.text('Tipo', 70, 165);
    doc.setFontSize(18);
    doc.text('Resultado', 120, 165);
    doc.setFontSize(18);
    doc.text('Amostra', 170, 165);

    // this.amostra.exames.forEach(value => {
    //   doc.setFontSize(16);
    //   doc.text(value.nome, 20, this.cont);
    //   doc.setFontSize(16);
    //   doc.text(value.tipo, 70, this.cont);
    //   doc.setFontSize(16);
    //   doc.text(value.resultado, 120, this.cont);
    //   doc.setFontSize(16);
    //   doc.text(value.subamostra.subAmostra, 170, this.cont);
    //   this.cont = this.cont + 10;
    //   console.log("Passou aqui");
    // });

    doc.save("Laudo_"+this.amostra.protocolo+".pdf");
    this.$router.go(0);
  }
}
