<template>
  <div>
    <h2 id="page-heading" data-cy="ProprietarioHeading">
      <span id="proprietario-heading">Proprietários</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-outline-success mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Atualizar lista</span>
        </button>
        <router-link :to="{ name: 'ProprietarioCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-outline-success jh-create-entity create-proprietario"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Criar um novo proprietário </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && proprietarios && proprietarios.length === 0">
      <span>Nenhum proprietário encontrado</span>
    </div>
    <div class="table-responsive" v-if="proprietarios && proprietarios.length > 0">
      <table class="table table-striped" aria-describedby="proprietarios">
        <thead>
          <tr>
            <!-- <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th> -->
            <th scope="row" v-on:click="changeOrder('nome')">
              <span>Nome</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nome'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('telefone')">
              <span>Telefone</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'telefone'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('email')">
              <span>E-mail</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'email'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('enviarLaudo')">
              <span>Enviar Laudo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'enviarLaudo'"></jhi-sort-indicator>
            </th>
            <!-- <th scope="row" v-on:click="changeOrder('propriedade.tipoPropriedade')">
              <span>Propriedade</span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'propriedade.tipoPropriedade'"
              ></jhi-sort-indicator>
            </th> -->
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="proprietario in proprietarios" :key="proprietario.id" data-cy="entityTable">
            <!-- <td>
              <router-link :to="{ name: 'ProprietarioView', params: { proprietarioId: proprietario.id } }">{{
                proprietario.id
              }}</router-link>
            </td> -->
            <td>{{ proprietario.nome }}</td>
            <td>{{ proprietario.telefone }}</td>
            <td>{{ proprietario.email }}</td>
            <td>{{ proprietario.enviarLaudo }}</td>
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
                  <button @click="navigate" class="btn btn-success btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">Ver</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ProprietarioEdit', params: { proprietarioId: proprietario.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Editar</span>
                  </button>
                </router-link>
                <b-button
                  v-if="verificaUsuario()"
                  v-on:click="prepareRemove(proprietario)"
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
        ><span id="rp6App.proprietario.delete.question" data-cy="proprietarioDeleteDialogHeading">Confirmação de exclusão</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-proprietario-heading">Você tem certeza que deseja deletar este proprietário?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancelar</button>
        <button
          type="button"
          class="btn btn-danger"
          id="jhi-confirm-delete-proprietario"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeProprietario()"
        >
          Deletar
        </button>
      </div>
    </b-modal>
    <div v-show="proprietarios && proprietarios.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./proprietario.component.ts"></script>
