<template>
  <div>
    <h2 id="page-heading" data-cy="ExameHeading">
      <span id="exame-heading">Exames</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'ExameCreate', params: { amostraId: amostraId } }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-exame"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Exame </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && exames && exames.length === 0">
      <span>No exames found</span>
    </div>
    <div class="table-responsive" v-if="exames && exames.length > 0">
      <table class="table table-striped" aria-describedby="exames">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Nome</span></th>
            <th scope="row"><span>Tipo</span></th>
            <th scope="row"><span>Resultado</span></th>
            <th scope="row"><span>Data Teste</span></th>
            <th scope="row"><span>Data Leitura</span></th>
            <th scope="row"><span>Preenchimento Espelho</span></th>
            <th scope="row"><span>Observacoes</span></th>
            <th scope="row"><span>Valor</span></th>
            <th scope="row"><span>Amostra</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="exame in exames" :key="exame.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ExameView', params: { exameId: exame.id } }">{{ exame.id }}</router-link>
            </td>
            <td>{{ exame.nome }}</td>
            <td>{{ exame.tipo }}</td>
            <td>{{ exame.resultado }}</td>
            <td>{{ exame.dataTeste }}</td>
            <td>{{ exame.dataLeitura }}</td>
            <td>{{ exame.preenchimentoEspelho }}</td>
            <td>{{ exame.observacoes }}</td>
            <td>{{ exame.valor }}</td>
            <td>
              <div v-if="exame.amostra">
                <router-link :to="{ name: 'AmostraView', params: { amostraId: exame.amostra.id } }">{{
                  exame.amostra.protocolo
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ExameView', params: { exameId: exame.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ExameEdit', params: { amostraId: amostraId, exameId: exame.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(exame)"
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
    <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityBackButton">
    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
  </button>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="rp6App.exame.delete.question" data-cy="exameDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-exame-heading">Are you sure you want to delete this Exame?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-exame"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeExame()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./exame.component.ts"></script>
