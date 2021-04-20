<template>
  <div>
    <h2 id="page-heading" data-cy="ProprietarioHeading">
      <span id="proprietario-heading">Proprietarios</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'ProprietarioCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-proprietario"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Proprietario </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && proprietarios && proprietarios.length === 0">
      <span>No proprietarios found</span>
    </div>
    <div class="table-responsive" v-if="proprietarios && proprietarios.length > 0">
      <table class="table table-striped" aria-describedby="proprietarios">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Nome</span></th>
            <th scope="row"><span>Telefone</span></th>
            <th scope="row"><span>Email</span></th>
            <th scope="row"><span>Enviar Laudo</span></th>
            <th scope="row"><span>Endereco</span></th>
            <th scope="row"><span>Propriedade</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="proprietario in proprietarios" :key="proprietario.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ProprietarioView', params: { proprietarioId: proprietario.id } }">{{
                proprietario.id
              }}</router-link>
            </td>
            <td>{{ proprietario.nome }}</td>
            <td>{{ proprietario.telefone }}</td>
            <td>{{ proprietario.email }}</td>
            <td>{{ proprietario.enviarLaudo }}</td>
            <td>
              <div v-if="proprietario.endereco">
                <router-link :to="{ name: 'EnderecoView', params: { enderecoId: proprietario.endereco.id } }">{{
                  proprietario.endereco.endereco
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="proprietario.propriedade">
                <router-link :to="{ name: 'PropriedadeView', params: { propriedadeId: proprietario.propriedade.id } }">{{
                  proprietario.propriedade.tipoPropriedade
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ProprietarioView', params: { proprietarioId: proprietario.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ProprietarioEdit', params: { proprietarioId: proprietario.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(proprietario)"
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
        ><span id="rp6App.proprietario.delete.question" data-cy="proprietarioDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-proprietario-heading">Are you sure you want to delete this Proprietario?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-proprietario"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeProprietario()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./proprietario.component.ts"></script>
