<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="rp6App.exame.home.createOrEditLabel" data-cy="ExameCreateUpdateHeading">Criar ou editar um exame</h2>
        <div>
          <!-- <div class="form-group" v-if="exame.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="exame.id" readonly />
          </div> -->
          <div class="form-group">
            <label class="form-control-label" for="exame-nome">Nome</label>
            <select
              class="form-control"
              id="exame-nome"
              data-cy="nome"
              name="nome"
              v-model="exame.nome"
              v-on:click="filtraTipoVirusPorTipoExame()"
            >
              <option value="SORONEUTRALIZACAO">Soroneutralização</option>
              <option value="ENSAIO_IMUNOABSORCAO_ENZIMATICA">Ensaio de Imunoabsorção Enzimática</option>
              <option value="REACAO_CADEIA_POLIMERASE">Reação em Cadeia de Polimerase</option>
              <option value="IMUNOCROMATOGRAFIA">Imunocromatografia</option>
              <option value="IMUNOFLUORESCENCIA">Imunofluorescência</option>
              <option value="INIBICAO_HEMAGLUTINACAO">Inibição da Hemaglutinação</option>
              <option value="ISOLAMENTO_VIRAL">Isolamento Viral</option>
              <option value="IMUNODIFUSAO_GEL_AGAR">Imunodifusão em Gel de Agar</option>
              <option value="MICROSCOPIA_ELETRONICA">Microscopia Eletrônica</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="exame-tipo">Tipo</label>
            <select class="form-control" id="exame-tipo" data-cy="tipo" name="tipo" v-model="exame.tipo">
              <option
                v-for="tipoVirus in tiposVirus"
                :key="tipoVirus"
                v-bind:value="exame.tipo && tipoVirus === exame.tipo ? exame.tipo : tipoVirus"
              >
                {{ tipoVirus }}
              </option>
            </select>
          </div>
          <!--          <div v-if="exame.nome === 'VACINA_AUTOGENA'">-->
          <!--            <div class="form-group">-->
          <!--              <label class="form-control-label" for="exame-pesoMaterial">Peso Material</label>-->
          <!--              <input-->
          <!--                type="text"-->
          <!--                class="form-control"-->
          <!--                name="pesoMaterial"-->
          <!--                id="exame-pesoMaterial"-->
          <!--                data-cy="pesoMaterial"-->
          <!--                :class="{ valid: !$v.exame.pesoMaterial.$invalid, invalid: $v.exame.pesoMaterial.$invalid }"-->
          <!--                v-model="$v.exame.pesoMaterial.$model"-->
          <!--              />-->
          <!--            </div>-->
          <!--            <div class="form-group">-->
          <!--              <label class="form-control-label" for="exame-estimativaVacinas">Estimativa Vacinas</label>-->
          <!--              <input-->
          <!--                type="text"-->
          <!--                class="form-control"-->
          <!--                name="estimativaVacinas"-->
          <!--                id="exame-estimativaVacinas"-->
          <!--                data-cy="estimativaVacinas"-->
          <!--                :class="{ valid: !$v.exame.estimativaVacinas.$invalid, invalid: $v.exame.estimativaVacinas.$invalid }"-->
          <!--                v-model="$v.exame.estimativaVacinas.$model"-->
          <!--              />-->
          <!--            </div>-->
          <!--          </div>-->
          <div class="form-group">
            <label class="form-control-label" for="exame-resultado">Resultado</label>
            <input
              type="text"
              class="form-control"
              name="resultado"
              id="exame-resultado"
              data-cy="resultado"
              :class="{ valid: !$v.exame.resultado.$invalid, invalid: $v.exame.resultado.$invalid }"
              v-model="$v.exame.resultado.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="exame-dataTeste">Data Teste</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="exame-dataTeste"
                  v-model="$v.exame.dataTeste.$model"
                  name="dataTeste"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="exame-dataTeste"
                data-cy="dataTeste"
                type="text"
                class="form-control"
                name="dataTeste"
                :class="{ valid: !$v.exame.dataTeste.$invalid, invalid: $v.exame.dataTeste.$invalid }"
                v-model="$v.exame.dataTeste.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="exame-dataLeitura">Data Leitura</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="exame-dataLeitura"
                  v-model="$v.exame.dataLeitura.$model"
                  name="dataLeitura"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="exame-dataLeitura"
                data-cy="dataLeitura"
                type="text"
                class="form-control"
                name="dataLeitura"
                :class="{ valid: !$v.exame.dataLeitura.$invalid, invalid: $v.exame.dataLeitura.$invalid }"
                v-model="$v.exame.dataLeitura.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="exame-preenchimentoEspelho">Preenchimento Espelho</label>
            <textarea
              class="form-control"
              name="preenchimentoEspelho"
              id="exame-preenchimentoEspelho"
              data-cy="preenchimentoEspelho"
              :class="{ valid: !$v.exame.preenchimentoEspelho.$invalid, invalid: $v.exame.preenchimentoEspelho.$invalid }"
              v-model="$v.exame.preenchimentoEspelho.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="exame-observacoes">Observações</label>
            <textarea
              class="form-control"
              name="observacoes"
              id="exame-observacoes"
              data-cy="observacoes"
              :class="{ valid: !$v.exame.observacoes.$invalid, invalid: $v.exame.observacoes.$invalid }"
              v-model="$v.exame.observacoes.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="exame-valor">Valor</label>
            <input
              type="number"
              class="form-control"
              name="valor"
              id="exame-valor"
              data-cy="valor"
              :class="{ valid: !$v.exame.valor.$invalid, invalid: $v.exame.valor.$invalid }"
              v-model.number="$v.exame.valor.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="exame-subamostra">Amostra</label>
            <select class="form-control" id="exame-subamostra" data-cy="subamostra" name="subamostra" v-model="exame.subamostra" required>
              <option v-bind:value="null"></option>
              <option
                v-bind:value="exame.subamostra && subamostraOption.id === exame.subamostra.id ? exame.subamostra : subamostraOption"
                v-for="subamostraOption in subamostras"
                :key="subamostraOption.id"
              >
                {{ subamostraOption.subAmostra }}
              </option>
            </select>
          </div>
          <!-- <div class="form-group">
            <label class="form-control-label" for="exame-amostra">Amostra</label>
            <select class="form-control" id="exame-amostra" data-cy="amostra" name="amostra" v-model="exame.amostra">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="exame.amostra && amostraOption.id === exame.amostra.id ? exame.amostra : amostraOption"
                v-for="amostraOption in amostras"
                :key="amostraOption.id"
              >
                {{ amostraOption.protocolo }}
              </option>
            </select>
          </div> -->
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-outline-danger" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancelar</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.exame.$invalid || isSaving"
            class="btn btn-success"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Salvar</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./exame-update.component.ts"></script>
