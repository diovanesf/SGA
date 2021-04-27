<template>
  <div>
    <h2 id="page-heading" data-cy="EnderecoHeading">
      <span id="endereco-heading">Endereços</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-outline-success mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Atualizar lista</span>
        </button>
        <router-link :to="{ name: 'EnderecoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-outline-success jh-create-entity create-endereco"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Criar endereço </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && enderecos && enderecos.length === 0">
      <span>Nenhum endereço encontrado</span>
    </div>
    <div class="table-responsive" v-if="enderecos && enderecos.length > 0">
      <table class="table table-striped" aria-describedby="enderecos">
        <thead>
          <tr>
            <!-- <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th> -->
            <th scope="row" v-on:click="changeOrder('endereco')">
              <span>Endereço</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'endereco'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cep')">
              <span>CEP</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cep'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cidade')">
              <span>Cidade</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cidade'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('estado')">
              <span>Estado</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estado'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('coordenadasGps')">
              <span>Coordenadas GPS</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'coordenadasGps'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="endereco in enderecos" :key="endereco.id" data-cy="entityTable">
            <!-- <td>
              <router-link :to="{ name: 'EnderecoView', params: { enderecoId: endereco.id } }">{{ endereco.id }}</router-link>
            </td> -->
            <td>{{ endereco.endereco }}</td>
            <td>{{ endereco.cep }}</td>
            <td>{{ endereco.cidade }}</td>
            <td>{{ endereco.estado }}</td>
            <td>{{ endereco.coordenadasGps }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EnderecoView', params: { enderecoId: endereco.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-success btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">Ver</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EnderecoEdit', params: { enderecoId: endereco.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Editar</span>
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
        ><span id="rp6App.endereco.delete.question" data-cy="enderecoDeleteDialogHeading">Confirmação de exclusão</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-endereco-heading">Você tem certeza que deseja deletar este endereco?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancelar</button>
        <button
          type="button"
          class="btn btn-danger"
          id="jhi-confirm-delete-endereco"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeEndereco()"
        >
          Deletar
        </button>
      </div>
    </b-modal>
    <div v-show="enderecos && enderecos.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./endereco.component.ts"></script>
