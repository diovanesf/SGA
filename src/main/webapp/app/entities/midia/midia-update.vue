<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="rp6App.midia.home.createOrEditLabel" data-cy="MidiaCreateUpdateHeading">Criar ou editar uma Mídia</h2>
        <div>
          <!-- <div class="form-group" v-if="midia.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="midia.id" readonly />
          </div> -->
          <div class="form-group">
            <label class="form-control-label" for="midia-nome">Nome</label>
            <input
              type="text"
              class="form-control"
              name="nome"
              id="midia-nome"
              data-cy="nome"
              :class="{ valid: !$v.midia.nome.$invalid, invalid: $v.midia.nome.$invalid }"
              v-model="$v.midia.nome.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="midia-descricao">Descrição</label>
            <input
              type="text"
              class="form-control"
              name="descricao"
              id="midia-descricao"
              data-cy="descricao"
              :class="{ valid: !$v.midia.descricao.$invalid, invalid: $v.midia.descricao.$invalid }"
              v-model="$v.midia.descricao.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="midia-file">Mídia</label>
            <div>
              <div v-if="midia.file" class="form-text clearfix">
                <span class="pull-left">Arquivo: {{ midia.fileContentType }} <br> Tamanho: {{ byteSize(midia.file) }}</span>
                <br>
                <button class = "btn btn-outline-success"><a v-on:click="openFile(midia.fileContentType, midia.file)">Abrir</a></button>
                <button
                  type="button"
                  v-on:click="
                    midia.file = null;
                    midia.fileContentType = null;
                  "
                  class="btn btn-outline-warning btn-xs pull-right"
                >Remover
                </button>
              </div>
              <br>
              <input type="file" ref="file_file" id="file_file" data-cy="file" v-on:change="setFileData($event, midia, 'file', false)" />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="file"
              id="midia-file"
              data-cy="file"
              :class="{ valid: !$v.midia.file.$invalid, invalid: $v.midia.file.$invalid }"
              v-model="$v.midia.file.$model"
            />
            <input type="hidden" class="form-control" name="fileContentType" id="midia-fileContentType" v-model="midia.fileContentType" />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="midia-amostra">Amostra</label>
            <select class="form-control" id="midia-amostra" data-cy="amostra" name="amostra" v-model="midia.amostra">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="midia.amostra && amostraOption.id === midia.amostra.id ? midia.amostra : amostraOption"
                v-for="amostraOption in amostras"
                :key="amostraOption.id"
              >
                {{ amostraOption.protocolo }}
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
            :disabled="$v.midia.$invalid || isSaving"
            class="btn btn-success"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Salvar</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./midia-update.component.ts"></script>
