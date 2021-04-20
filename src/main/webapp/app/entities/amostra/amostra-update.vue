<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="rp6App.amostra.home.createOrEditLabel" data-cy="AmostraCreateUpdateHeading">Create or edit a Amostra</h2>
        <div>
          <div class="form-group" v-if="amostra.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="amostra.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-protocolo">Protocolo</label>
            <input
              type="text"
              class="form-control"
              name="protocolo"
              id="amostra-protocolo"
              data-cy="protocolo"
              :class="{ valid: !$v.amostra.protocolo.$invalid, invalid: $v.amostra.protocolo.$invalid }"
              v-model="$v.amostra.protocolo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-formaEnvio">Forma Envio</label>
            <input
              type="text"
              class="form-control"
              name="formaEnvio"
              id="amostra-formaEnvio"
              data-cy="formaEnvio"
              :class="{ valid: !$v.amostra.formaEnvio.$invalid, invalid: $v.amostra.formaEnvio.$invalid }"
              v-model="$v.amostra.formaEnvio.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-numeroAmostras">Numero Amostras</label>
            <input
              type="number"
              class="form-control"
              name="numeroAmostras"
              id="amostra-numeroAmostras"
              data-cy="numeroAmostras"
              :class="{ valid: !$v.amostra.numeroAmostras.$invalid, invalid: $v.amostra.numeroAmostras.$invalid }"
              v-model.number="$v.amostra.numeroAmostras.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-especie">Especie</label>
            <input
              type="text"
              class="form-control"
              name="especie"
              id="amostra-especie"
              data-cy="especie"
              :class="{ valid: !$v.amostra.especie.$invalid, invalid: $v.amostra.especie.$invalid }"
              v-model="$v.amostra.especie.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-dataInicial">Data Inicial</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="amostra-dataInicial"
                  v-model="$v.amostra.dataInicial.$model"
                  name="dataInicial"
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
                id="amostra-dataInicial"
                data-cy="dataInicial"
                type="text"
                class="form-control"
                name="dataInicial"
                :class="{ valid: !$v.amostra.dataInicial.$invalid, invalid: $v.amostra.dataInicial.$invalid }"
                v-model="$v.amostra.dataInicial.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-dataFinal">Data Final</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="amostra-dataFinal"
                  v-model="$v.amostra.dataFinal.$model"
                  name="dataFinal"
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
                id="amostra-dataFinal"
                data-cy="dataFinal"
                type="text"
                class="form-control"
                name="dataFinal"
                :class="{ valid: !$v.amostra.dataFinal.$invalid, invalid: $v.amostra.dataFinal.$invalid }"
                v-model="$v.amostra.dataFinal.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-materialRecebido">Material Recebido</label>
            <input
              type="text"
              class="form-control"
              name="materialRecebido"
              id="amostra-materialRecebido"
              data-cy="materialRecebido"
              :class="{ valid: !$v.amostra.materialRecebido.$invalid, invalid: $v.amostra.materialRecebido.$invalid }"
              v-model="$v.amostra.materialRecebido.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-acondicionamento">Acondicionamento</label>
            <input
              type="text"
              class="form-control"
              name="acondicionamento"
              id="amostra-acondicionamento"
              data-cy="acondicionamento"
              :class="{ valid: !$v.amostra.acondicionamento.$invalid, invalid: $v.amostra.acondicionamento.$invalid }"
              v-model="$v.amostra.acondicionamento.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-condicaoMaterial">Condicao Material</label>
            <input
              type="text"
              class="form-control"
              name="condicaoMaterial"
              id="amostra-condicaoMaterial"
              data-cy="condicaoMaterial"
              :class="{ valid: !$v.amostra.condicaoMaterial.$invalid, invalid: $v.amostra.condicaoMaterial.$invalid }"
              v-model="$v.amostra.condicaoMaterial.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-status">Status</label>
            <input
              type="text"
              class="form-control"
              name="status"
              id="amostra-status"
              data-cy="status"
              :class="{ valid: !$v.amostra.status.$invalid, invalid: $v.amostra.status.$invalid }"
              v-model="$v.amostra.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-tipoMedVet">Tipo Med Vet</label>
            <input
              type="text"
              class="form-control"
              name="tipoMedVet"
              id="amostra-tipoMedVet"
              data-cy="tipoMedVet"
              :class="{ valid: !$v.amostra.tipoMedVet.$invalid, invalid: $v.amostra.tipoMedVet.$invalid }"
              v-model="$v.amostra.tipoMedVet.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-valorTotal">Valor Total</label>
            <input
              type="number"
              class="form-control"
              name="valorTotal"
              id="amostra-valorTotal"
              data-cy="valorTotal"
              :class="{ valid: !$v.amostra.valorTotal.$invalid, invalid: $v.amostra.valorTotal.$invalid }"
              v-model.number="$v.amostra.valorTotal.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-tipoPagamento">Tipo Pagamento</label>
            <input
              type="text"
              class="form-control"
              name="tipoPagamento"
              id="amostra-tipoPagamento"
              data-cy="tipoPagamento"
              :class="{ valid: !$v.amostra.tipoPagamento.$invalid, invalid: $v.amostra.tipoPagamento.$invalid }"
              v-model="$v.amostra.tipoPagamento.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-situacao">Situacao</label>
            <input
              type="text"
              class="form-control"
              name="situacao"
              id="amostra-situacao"
              data-cy="situacao"
              :class="{ valid: !$v.amostra.situacao.$invalid, invalid: $v.amostra.situacao.$invalid }"
              v-model="$v.amostra.situacao.$model"
            />
          </div>
          <div class="form-group">
            <label for="amostra-user">User</label>
            <select
              class="form-control"
              id="amostra-user"
              data-cy="user"
              multiple
              name="user"
              v-if="amostra.users !== undefined"
              v-model="amostra.users"
            >
              <option v-bind:value="getSelected(amostra.users, userOption)" v-for="userOption in users" :key="userOption.id">
                {{ userOption.login }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-propriedade">Propriedade</label>
            <select class="form-control" id="amostra-propriedade" data-cy="propriedade" name="propriedade" v-model="amostra.propriedade">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  amostra.propriedade && propriedadeOption.id === amostra.propriedade.id ? amostra.propriedade : propriedadeOption
                "
                v-for="propriedadeOption in propriedades"
                :key="propriedadeOption.id"
              >
                {{ propriedadeOption.tipoPropriedade }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-medicoveterinario">Medicoveterinario</label>
            <select
              class="form-control"
              id="amostra-medicoveterinario"
              data-cy="medicoveterinario"
              name="medicoveterinario"
              v-model="amostra.medicoveterinario"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  amostra.medicoveterinario && medicoveterinarioOption.id === amostra.medicoveterinario.id
                    ? amostra.medicoveterinario
                    : medicoveterinarioOption
                "
                v-for="medicoveterinarioOption in medicoveterinarios"
                :key="medicoveterinarioOption.id"
              >
                {{ medicoveterinarioOption.nome }}
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
            :disabled="$v.amostra.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./amostra-update.component.ts"></script>
