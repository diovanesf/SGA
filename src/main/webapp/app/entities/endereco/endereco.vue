<template>
  <div>
    <h2 id="page-heading" data-cy="EnderecoHeading">
      <span id="endereco-heading">Enderecos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'EnderecoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-endereco"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Endereco </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && enderecos && enderecos.length === 0">
      <span>No enderecos found</span>
    </div>
    <div class="table-responsive" v-if="enderecos && enderecos.length > 0">
      <table class="table table-striped" aria-describedby="enderecos">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Endereco</span></th>
            <th scope="row"><span>Cep</span></th>
            <th scope="row"><span>Cidade</span></th>
            <th scope="row"><span>Estado</span></th>
            <th scope="row"><span>Coordenadas Gps</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="endereco in enderecos" :key="endereco.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EnderecoView', params: { enderecoId: endereco.id } }">{{ endereco.id }}</router-link>
            </td>
            <td>{{ endereco.endereco }}</td>
            <td>{{ endereco.cep }}</td>
            <td>{{ endereco.cidade }}</td>
            <td>{{ endereco.estado }}</td>
            <td>{{ endereco.coordenadasGps }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EnderecoView', params: { enderecoId: endereco.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EnderecoEdit', params: { enderecoId: endereco.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(endereco)"
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
        ><span id="rp6App.endereco.delete.question" data-cy="enderecoDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-endereco-heading">Are you sure you want to delete this Endereco?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-endereco"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeEndereco()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./endereco.component.ts"></script>
