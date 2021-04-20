<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="rp6App.exame.home.createOrEditLabel" data-cy="ExameCreateUpdateHeading">Create or edit a Exame</h2>
        <div>
          <div class="form-group" v-if="exame.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="exame.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="exame-nome">Nome</label>
            <input
              type="text"
              class="form-control"
              name="nome"
              id="exame-nome"
              data-cy="nome"
              :class="{ valid: !$v.exame.nome.$invalid, invalid: $v.exame.nome.$invalid }"
              v-model="$v.exame.nome.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="exame-tipo">Tipo</label>
            <input
              type="text"
              class="form-control"
              name="tipo"
              id="exame-tipo"
              data-cy="tipo"
              :class="{ valid: !$v.exame.tipo.$invalid, invalid: $v.exame.tipo.$invalid }"
              v-model="$v.exame.tipo.$model"
            />
          </div>
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
            <label class="form-control-label" for="exame-observacoes">Observacoes</label>
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
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.exame.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./exame-update.component.ts"></script>
