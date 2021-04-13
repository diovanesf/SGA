<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="rp6App.midia.home.createOrEditLabel" data-cy="MidiaCreateUpdateHeading">Create or edit a Midia</h2>
        <div>
          <div class="form-group" v-if="midia.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="midia.id" readonly />
          </div>
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
            <label class="form-control-label" for="midia-descricao">Descricao</label>
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
            <label class="form-control-label" for="midia-file">File</label>
            <div>
              <div v-if="midia.file" class="form-text text-danger clearfix">
                <a class="pull-left" v-on:click="openFile(midia.fileContentType, midia.file)">open</a><br />
                <span class="pull-left">{{ midia.fileContentType }}, {{ byteSize(midia.file) }}</span>
                <button
                  type="button"
                  v-on:click="
                    midia.file = null;
                    midia.fileContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
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
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.midia.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./midia-update.component.ts"></script>
