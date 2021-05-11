<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="rp6App.vacina.home.createOrEditLabel" data-cy="VacinaCreateUpdateHeading">Criar ou editar uma Vacina</h2>
        <div>
          <div class="form-group" v-if="vacina.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="vacina.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vacina-status">Status</label>
            <input
              type="text"
              class="form-control"
              name="status"
              id="vacina-status"
              data-cy="status"
              :class="{ valid: !$v.vacina.status.$invalid, invalid: $v.vacina.status.$invalid }"
              v-model="$v.vacina.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vacina-pesoMaterial">Peso do Material</label>
            <input
              type="text"
              class="form-control"
              name="pesoMaterial"
              id="vacina-pesoMaterial"
              data-cy="pesoMaterial"
              :class="{ valid: !$v.vacina.pesoMaterial.$invalid, invalid: $v.vacina.pesoMaterial.$invalid }"
              v-model="$v.vacina.pesoMaterial.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vacina-estimativaVacinas">Estimativa Vacinas</label>
            <input
              type="text"
              class="form-control"
              name="estimativaVacinas"
              id="vacina-estimativaVacinas"
              data-cy="estimativaVacinas"
              :class="{ valid: !$v.vacina.estimativaVacinas.$invalid, invalid: $v.vacina.estimativaVacinas.$invalid }"
              v-model="$v.vacina.estimativaVacinas.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vacina-dataConclusao">Data de Conclusão</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="vacina-dataConclusao"
                  v-model="$v.vacina.dataConclusao.$model"
                  name="dataConclusao"
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
                id="vacina-dataConclusao"
                data-cy="dataConclusao"
                type="text"
                class="form-control"
                name="dataConclusao"
                :class="{ valid: !$v.vacina.dataConclusao.$invalid, invalid: $v.vacina.dataConclusao.$invalid }"
                v-model="$v.vacina.dataConclusao.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vacina-observacoes">Observações</label>
            <textarea
              class="form-control"
              name="observacoes"
              id="vacina-observacoes"
              data-cy="observacoes"
              :class="{ valid: !$v.vacina.observacoes.$invalid, invalid: $v.vacina.observacoes.$invalid }"
              v-model="$v.vacina.observacoes.$model"
            ></textarea>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-outline-danger" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancelar</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.vacina.$invalid || isSaving"
            class="btn btn-success"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Salvar</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./vacina-update.component.ts"></script>
