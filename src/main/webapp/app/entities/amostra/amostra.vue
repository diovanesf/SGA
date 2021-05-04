<template>
  <div>
    <h2 id="page-heading" data-cy="AmostraHeading">
      <span id="amostra-heading">Amostras</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span>Atualizar Lista</span>
        </button>
        <router-link :to="{ name: 'AmostraCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-outline-success jh-create-entity create-amostra"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Criar uma nova amostra </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && amostras && amostras.length === 0">
      <span>Nenhuma amostra encontrada</span>
    </div>
    <div class="table-responsive" v-if="amostras && amostras.length > 0">
      <table class="table table-striped" aria-describedby="amostras">
        <thead>
          <tr>
            <!-- <th scope="row" v-on:click="changeOrder('id')">
            <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
          </th> -->
            <th scope="row" v-on:click="changeOrder('protocolo')">
              <span>Protocolo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'protocolo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('formaEnvio')">
              <span>Forma de envio</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'formaEnvio'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('numeroAmostras')">
              <span>Número de amostras</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'numeroAmostras'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('especie')">
              <span>Espécie</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'especie'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dataInicial')">
              <span>Data inicial</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dataInicial'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('materialRecebido')">
              <span>Material recebido</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'materialRecebido'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acondicionamento')">
              <span>Acondicionamento</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acondicionamento'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('condicaoMaterial')">
              <span>Condição do material</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'condicaoMaterial'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('numeroAnimais')">
              <span>Numero Animais</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'numeroAnimais'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acometidos')">
              <span>Acometidos</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acometidos'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('pricipalSuspeita')">
              <span>Pricipal Suspeita</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pricipalSuspeita'"></jhi-sort-indicator>
            </th>

            <th scope="row" v-on:click="changeOrder('tipo')">
              <span>Tipo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tipo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span>Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tipoMedVet')">
              <span>Tipo Med. Vet.</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tipoMedVet'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('valorTotal')">
              <span>Valor total</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'valorTotal'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tipoPagamento')">
              <span>Tipo do pagamento</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tipoPagamento'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('situacao')">
              <span>Situação</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'situacao'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('propriedade.tipoPropriedade')">
              <span>Propriedade</span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'propriedade.tipoPropriedade'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('medicoveterinario.nome')">
              <span>Médico veterinário</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'medicoveterinario.nome'"></jhi-sort-indicator>
            </th>
            <th scope="row"><span>Usuário</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="amostra in amostras" :key="amostra.id" data-cy="entityTable">
            <!-- <td>
            <router-link :to="{ name: 'AmostraView', params: { amostraId: amostra.id } }">{{ amostra.id }}</router-link>
          </td> -->
            <td>{{ amostra.protocolo }}</td>
            <td>{{ amostra.formaEnvio }}</td>
            <td>{{ amostra.numeroAmostras }}</td>
            <td>{{ amostra.especie }}</td>
            <td>{{ amostra.dataInicial }}</td>
            <td>{{ amostra.dataFinal }}</td>
            <td>{{ amostra.materialRecebido }}</td>
            <td>{{ amostra.acondicionamento }}</td>
            <td>{{ amostra.condicaoMaterial }}</td>
            <td>{{ amostra.numeroAnimais }}</td>
            <td>{{ amostra.acometidos }}</td>
            <td>{{ amostra.pricipalSuspeita }}</td>
            <td>{{ amostra.tipo }}</td>
            <td>{{ amostra.status }}</td>
            <td>{{ amostra.tipoMedVet }}</td>
            <td>{{ amostra.valorTotal }}</td>
            <td>{{ amostra.tipoPagamento }}</td>
            <td>{{ amostra.situacao }}</td>
            <td>
              <div v-if="amostra.propriedade">
                <router-link :to="{ name: 'PropriedadeView', params: { propriedadeId: amostra.propriedade.id } }"
                  >{{ amostra.propriedade.endereco.endereco }}
                </router-link>
              </div>
            </td>
            <td>
              <div v-if="amostra.medicoveterinario">
                <router-link :to="{ name: 'MedicoveterinarioView', params: { medicoveterinarioId: amostra.medicoveterinario.id } }">
                  {{ amostra.medicoveterinario.nome }}
                </router-link>
              </div>
            </td>
            <td>
              <span v-for="(user, i) in amostra.users" :key="user.id">{{ i > 0 ? ', ' : '' }}{{ user.login }}</span>
            </td>
            <td></td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  v-if="verificaExames(amostra) && verificaUsuario()"
                  :to="{ name: 'Laudo', params: { amostraId: amostra.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data- cy="entityDetailsButton">
                    <font-awesome-icon icon="book"></font-awesome-icon>
                    <span class="d-none d-md-inline">Gerar Laudo</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'Exame', params: { amostraId: amostra.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-warning btn-sm details" data- cy="entityDetailsButton">
                    <font-awesome-icon icon="tasks"></font-awesome-icon>
                    <span class="d-none d-md-inline">Exames</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AmostraView', params: { amostraId: amostra.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-success btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">Ver</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AmostraEdit', params: { amostraId: amostra.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Editar</span>
                  </button>
                </router-link>
                <b-button
                  v-if="verificaUsuario()"
                  v-on:click="prepareRemove(amostra)"
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
        ><span id="rp6App.amostra.delete.question" data-cy="amostraDeleteDialogHeading">Confirmar exclusão</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-amostra-heading">Tem certeza que deseja deletar esta Amostra?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancelar</button>
        <button
          type="button"
          class="btn btn-danger"
          id="jhi-confirm-delete-amostra"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeAmostra()"
        >
          Deletar
        </button>
      </div>
    </b-modal>
    <div v-show="amostras && amostras.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./amostra.component.ts"></script>
