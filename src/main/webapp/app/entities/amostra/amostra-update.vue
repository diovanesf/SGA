<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" validate v-on:submit.prevent="save()">
        <h2 id="rp6App.amostra.home.createOrEditLabel" data-cy="AmostraCreateUpdateHeading">Criar ou editar uma Amostra</h2>
        <div>
          <!-- <div class="form-group" v-if="amostra.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="amostra.id" readonly />
          </div> -->
          <div class="form-group" v-if="amostra.protocolo">
            <label class="form-control-label" for="amostra-protocolo">Protocolo</label>
            <input
              type="text"
              class="form-control"
              name="protocolo"
              id="amostra-protocolo"
              data-cy="protocolo"
              :class="{ valid: !$v.amostra.protocolo.$invalid, invalid: $v.amostra.protocolo.$invalid }"
              v-model="$v.amostra.protocolo.$model"
              readonly
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-formaEnvio">Forma de envio</label>
            <select class="form-control" id="amostra-formaEnvio" data-cy="formaEnvio" name="formaEnvio" v-model="amostra.formaEnvio" required="true">
              <option value="CORREIOS">Correios</option>
              <option value="RODOVIARIA">Rodoviária</option>
              <option value="TRANSPORTADORA">Transportadora</option>
              <option value="ENTREGA_LABORATORIO">Entrega no laboratório</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-numeroAmostras">Número de amostras</label>
            <input
              type="number"
              class="form-control"
              name="numeroAmostras"
              id="amostra-numeroAmostras"
              data-cy="numeroAmostras"
              :class="{ valid: !$v.amostra.numeroAmostras.$invalid, invalid: $v.amostra.numeroAmostras.$invalid }"
              v-model.number="$v.amostra.numeroAmostras.$model"
              required="true"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-especie">Espécie</label>
            <select class="form-control" id="amostra-especie-select" data-cy="especie" name="especie-select" v-model="amostra.especie" required="true">
              <option value="BOVINA">Bovina</option>
              <option value="EQUINA">Equina</option>
              <option value="OVINA">Ovina</option>
              <option value="SUINA">Suína</option>
              <option value="CANINA">Canina</option>
              <option value="FELINA">Felina</option>
              <option value="SELVAGENS">Selvagens</option>
              <option value="MORCEGOS">Morcegos</option>
              <option value="OUTRO">Outro</option>
            </select>
          </div>
          <div class="form-group" v-if="amostra.dataInicial">
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
                readonly
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-materialRecebido">Material Recebido</label>
            <select
              class="form-control"
              id="amostra-materialRecebido"
              data-cy="materialRecebido"
              name="materialRecebido"
              v-model="amostra.materialRecebido"
              required="true"
            >
              <option value="SANGUE_TOTAL">Sangue total</option>
              <option value="SORO">Soro</option>
              <option value="CROSTAS">Crostas</option>
              <option value="NEOPLASIAS">Neoplasias</option>
              <option value="TECIDOS">Tecidos</option>
              <option value="SWAB_NASAL">Swab Nasal</option>
              <option value="SWAB_OCULAR">Swab Ocular</option>
              <option value="SWAB_VESICULAS_LESOES">Swab de Vesículas/Lesões</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-acondicionamento">Acondicionamento</label>
            <select
              class="form-control"
              id="amostra-acondicionamento"
              data-cy="acondicionamento"
              name="acondicionamento"
              v-model="amostra.acondicionamento"
              required="true"
            >
              <option value="REFRIGERADA">Refrigerada</option>
              <option value="CONGELADA">Congelada</option>
              <option value="TEMPERATURA_AMBIENTE">Temperatura Ambiente</option>
              <option value="FORMOL">Formol</option>
              <option value="PARAFINIZADA">Parafinizada</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-condicaoMaterial">Condição do material</label>
            <select
              class="form-control"
              id="amostra-condicaoMaterial"
              data-cy="condicaoMaterial"
              name="condicaoMaterial"
              v-model="amostra.condicaoMaterial"
              required="true"
            >
              <option value="BOM">Bom</option>
              <option value="HEMOLISADO">Hemolisado</option>
              <option value="COAGULADO">Coagulado</option>
              <option value="PUTREFAÇÃO">Putrefação</option>
              <option value="SWAB_SECO">Swab Seco</option>
              <option value="DESCONGELADO">Descongelado</option>
              <option value="OUTRO">Outro</option>
            </select>
          </div>

          <div class="form-group">
            <label class="form-control-label" for="amostra-numeroAnimais">Numero Animais</label>
            <input
              type="number"
              class="form-control"
              name="numeroAnimais"
              id="amostra-numeroAnimais"
              data-cy="numeroAnimais"
              :class="{ valid: !$v.amostra.numeroAnimais.$invalid, invalid: $v.amostra.numeroAnimais.$invalid }"
              v-model.number="$v.amostra.numeroAnimais.$model"
              required="true"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-acometidos">Acometidos</label>
            <input
              type="text"
              class="form-control"
              name="acometidos"
              id="amostra-acometidos"
              data-cy="acometidos"
              :class="{ valid: !$v.amostra.acometidos.$invalid, invalid: $v.amostra.acometidos.$invalid }"
              v-model="$v.amostra.acometidos.$model"
              required="true"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-pricipalSuspeita">Pricipal Suspeita</label>
            <input
              type="text"
              class="form-control"
              name="pricipalSuspeita"
              id="amostra-pricipalSuspeita"
              data-cy="pricipalSuspeita"
              :class="{ valid: !$v.amostra.pricipalSuspeita.$invalid, invalid: $v.amostra.pricipalSuspeita.$invalid }"
              v-model="$v.amostra.pricipalSuspeita.$model"
              required="true"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-tipo">Tipo</label>
            <input
              type="text"
              class="form-control"
              name="tipo"
              id="amostra-tipo"
              data-cy="tipo"
              :class="{ valid: !$v.amostra.tipo.$invalid, invalid: $v.amostra.tipo.$invalid }"
              v-model="$v.amostra.tipo.$model"
              required="true"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-status">Status</label>
            <select class="form-control" id="amostra-status" data-cy="status" name="status" v-model="amostra.status" required="true">
              <option value="ACEITO">Aceito</option>
              <option value="RECUSADO">Recusado</option>
              <option value="AGUARDANDO_SOLICITACAO_HISTORICO">Aguardando solicitação/histórico</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-tipoMedVet">Tipo Med. Vet.</label>
            <select
              class="form-control"
              id="amostra-tipoMedVet"
              data-cy="tipoMedVet"
              name="tipoMedVet"
              v-model="amostra.tipoMedVet"
              v-on:click="setMedVet()"
              required="true"
            >
              <option value="MESMO_PROPRIETARIO">Mesmo do proprietário</option>
              <option value="SEM_MED_VET">Não há veterinário responsavel</option>
              <option value="COM_MED_VET">Há veterinário responsável</option>
            </select>
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
            <label class="form-control-label" for="amostra-tipoPagamento">Tipo de pagamento</label>
            <select
              class="form-control"
              id="amostra-tipoPagamento"
              data-cy="tipoPagamento"
              name="tipoPagamento"
              v-model="amostra.tipoPagamento"
            >
              <option value="DEPOSITO">Depósito bancário</option>
              <option value="DINHEIRO">Dinheiro</option>
              <option value="CARTAO">Cartão</option>
              <option value="ISENTO">Isento</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="amostra-situacao">Situação</label>
            <select class="form-control" id="amostra-situacao" data-cy="situacao" name="situacao" v-model="amostra.situacao">
              <option value="PAGO">Pago</option>
              <option value="AGUARDANDO_PAGAMENTO">Aguardando pagamento</option>
            </select>
          </div>
          <!-- <div class="form-group">
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
          </div> -->
          <div class="form-group">
            <label class="form-control-label" for="amostra-propriedade">Propriedade</label>
            <select class="form-control" id="amostra-propriedade" data-cy="propriedade" name="propriedade" v-model="amostra.propriedade" required="true">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  amostra.propriedade && propriedadeOption.id === amostra.propriedade.id ? amostra.propriedade : propriedadeOption
                "
                v-for="propriedadeOption in propriedades"
                :key="propriedadeOption.id"
              >
                {{ propriedadeOption.endereco.endereco }}
              </option>
            </select>
          </div>
          <div class="form-group" v-if="amostra.tipoMedVet === 'COM_MED_VET'">
            <label class="form-control-label" for="amostra-medicoveterinario">Médico Veterinário</label>

            <select
              class="form-control"
              id="amostra-medicoveterinario"
              data-cy="medicoveterinario"
              name="medicoveterinario"
              v-model="amostra.medicoveterinario"
              required="true"
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
          <button type="button" id="cancel-save" class="btn btn-outline-danger" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancelar</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.amostra.$invalid || isSaving"
            class="btn btn-success"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Salvar</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./amostra-update.component.ts"></script>
