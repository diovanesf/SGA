<template>
  <div>
    <h2 id="page-heading" data-cy="SubamostraHeading">
      <span id="subamostra-heading">Subamostras</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-outline-success mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Atualizar Lista</span>
        </button>
        <router-link :to="{ name: 'SubamostraCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-outline-success jh-create-entity create-subamostra"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Criar nova Subamostra </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && subamostras && subamostras.length === 0">
      <span>Nenhuma subamostra encontrada</span>
    </div>
    <div class="table-responsive" v-if="subamostras && subamostras.length > 0">
      <table class="table table-striped" aria-describedby="subamostras">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Subamostra</span></th>
            <th scope="row"><span>Amostra</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="subamostra in subamostras" :key="subamostra.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SubamostraView', params: { subamostraId: subamostra.id } }">{{ subamostra.id }}</router-link>
            </td>
            <td>{{ subamostra.subAmostra }}</td>
            <td>
              <div v-if="subamostra.amostra">
                <router-link :to="{ name: 'AmostraView', params: { amostraId: subamostra.amostra.id } }">{{
                  subamostra.amostra.protocolo
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SubamostraView', params: { subamostraId: subamostra.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-outline-success btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">Ver</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SubamostraEdit', params: { subamostraId: subamostra.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-warning btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Editar</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(subamostra)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Deletar</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="rp6App.subamostra.delete.question" data-cy="subamostraDeleteDialogHeading">Confirmação de exclusão</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-subamostra-heading">Você tem certeza que deseja deletar esta Subamostra?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancelar</button>
        <button
          type="button"
          class="btn btn-danger"
          id="jhi-confirm-delete-subamostra"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeSubamostra()"
        >
          Deletar
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./subamostra.component.ts"></script>
