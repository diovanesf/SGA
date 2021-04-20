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
            <select class="form-control" id="exame-nome" data-cy="nome" name="nome" v-model="exame.nome">
              <option
                v-bind:value="Soroneutralizacao"
              >Soroneutralizacao
              </option>
              <option
                v-bind:value="Ensaio_Imunoabsorcao_Enzimatica"
              >Ensaio de Imunoabsorcao Enzimatica
              </option>
              <option
                v-bind:value="Reacao_Cadeia_Polimerase"
              >Reacao em Cadeia de Polimerase
              </option>
              <option
                v-bind:value="Imunocromatografia"
              >Imunocromatografia
              </option>
              <option
                v-bind:value="Imunofluorescencia"
              >Imunofluorescencia
              </option>
              <option
                v-bind:value="Inibicao_Hemaglutinacao"
              >Inibicao da Hemaglutinacao
              </option>
              <option
                v-bind:value="Isolamento_Viral"
              >Isolamento Viral
              </option>
              <option
                v-bind:value="Imunodifusao_Gel_Agar"
              >Imunodifusao em Gel de Agar
              </option>
              <option
                v-bind:value="Microscopia_Eletronica"
              >Microscopia Eletronica
              </option>
            </select>
          </div>
        
          <div class="form-group">
            <label class="form-control-label" for="exame-tipo">Tipo</label>
            <select class="form-control" id="exame-tipo" data-cy="tipo" name="tipo" v-model="exame.tipo">
              
              <div v-show="exame.nome === 'Soroneutralizacao'">
              <option
                value="BVDV"
              >BVDV
              </option>
              <option
                value="IBR"
              >IBR
              </option>
              <option
                v-bind:value="EHV"
              >EHV
              </option>
              <option
                v-bind:value="EAV"
              >EAV
              </option>
              </div>
              
              <div v-show="exame.nome === 'Ensaio_Imunoabsorcao_Enzimatica'">
              <option
                v-bind:value="BVDV"
              >BVDV
              </option>
              <option
                v-bind:value="IBR"
              >IBR
              </option>
              <option
                v-bind:value="LEB"
              >LEB
              </option>
              </div>

              <div v-show="exame.nome === 'Reacao_Cadeia_Polimerase'">
              <option
                v-bind:value="BVDV"
              >BVDV
              </option>
              <option
                v-bind:value="IBR"
              >IBR
              </option>
              <option
                v-bind:value="CDV"
              >CDV
              </option>
              <option
                v-bind:value="EHV"
              >EHV
              </option>
              <option
                v-bind:value="AIE"
              >AIE
              </option>
              <option
                v-bind:value="FCM"
              >FCM
              </option>
              <option
                v-bind:value="BoHV-5"
              >BoHV-5
              </option>
              <option
                v-bind:value="ORFV"
              >ORFV
              </option>
              </div>

              <div v-show="exame.nome === 'Imunocromatografia'">
              <option
                v-bind:value="CDV"
              >CDV
              </option>
              <option
                v-bind:value="FIV/FELV"
              >FIV/FELV
              </option>
              </div>

              <div v-show="exame.nome === 'Imunofluorescencia'">
              <option
                v-bind:value="RABV"
              >RABV
              </option>
              </div>

              <div v-if="exame.nome === 'Inibicao_Hemaglutinacao'">
              <option
                v-bind:value="Influenza_Equina"
              >Influenza equina
              </option>
              </div>

              <div v-if="exame.nome === 'Isolamento_Viral'">
              <option
                v-bind:value="BVDV"
              >BVDV
              </option>
              <option
                v-bind:value="IBR"
              >IBR
              </option>
              <option
                v-bind:value="CPV"
              >CPV
              </option>
              <option
                v-bind:value="BRSV"
              >BRSV
              </option>
              </div>

              <div v-if="exame.nome === 'Imunodifusao_Gel_Agar'">
              <option
                v-bind:value="LEB"
              >LEB
              </option>
              </div>

              <div v-if="exame.nome === 'Microcospia_Eletronica'">
              <option
                v-bind:value="Coronavirus"
              >Coronavirus
              </option>
              <option
                v-bind:value="Rotavirus"
              >Rotavirus
              </option>
              </div>

            </select>
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
