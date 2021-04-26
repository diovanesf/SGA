<template>
  <div>
    <h2 id="page-heading" data-cy="MidiaHeading">
      <span id="midia-heading">Midias</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'MidiaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-midia"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Midia </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && midias && midias.length === 0">
      <span>No midias found</span>
    </div>
    <div class="table-responsive" v-if="midias && midias.length > 0">
      <table class="table table-striped" aria-describedby="midias">
        <thead>
          <tr>
            <!-- <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th> -->
            <th scope="row" v-on:click="changeOrder('nome')">
              <span>Nome</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nome'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('descricao')">
              <span>Descricao</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descricao'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('file')">
              <span>File</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'file'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('amostra.protocolo')">
              <span>Amostra</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'amostra.protocolo'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="midia in midias" :key="midia.id" data-cy="entityTable">
            <!-- <td>
              <router-link :to="{ name: 'MidiaView', params: { midiaId: midia.id } }">{{ midia.id }}</router-link>
            </td> -->
            <td>{{ midia.nome }}</td>
            <td>{{ midia.descricao }}</td>
            <td>
              <a v-if="midia.file" v-on:click="openFile(midia.fileContentType, midia.file)">open</a>
              <span v-if="midia.file">{{ midia.fileContentType }}, {{ byteSize(midia.file) }}</span>
            </td>
            <td>
              <div v-if="midia.amostra">
                <router-link :to="{ name: 'AmostraView', params: { amostraId: midia.amostra.id } }">{{
                  midia.amostra.protocolo
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MidiaView', params: { midiaId: midia.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MidiaEdit', params: { midiaId: midia.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(midia)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="rp6App.midia.delete.question" data-cy="midiaDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-midia-heading">Are you sure you want to delete this Midia?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-midia"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeMidia()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="midias && midias.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./midia.component.ts"></script>
