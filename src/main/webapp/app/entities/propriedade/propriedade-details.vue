<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="propriedade">
        <h2 class="jh-entity-heading" data-cy="propriedadeDetailsHeading"><span>Propriedade</span> {{ propriedade.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Tipo de Propriedade</span>
          </dt>
          <dd>
            <span>{{ propriedade.tipoPropriedade }}</span>
          </dd>
          </dd>
          <dt>
            <span>Tipo de Criação</span>
          </dt>
          <dd>
            <span>{{ propriedade.tipoCriacao }}</span>
          </dd>
          <dt>
            <span>Proprietário</span>
          </dt>
          <dd>
            <div v-if="propriedade.proprietario">
              <router-link :to="{ name: 'ProprietarioView', params: { proprietarioId: propriedade.proprietario.id } }">{{
                propriedade.proprietario.nome
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Endereço</span>
          </dt>
          <dd>
            <div v-if="propriedade.endereco">
              <router-link :to="{ name: 'EnderecoView', params: { enderecoId: propriedade.endereco.id } }">{{
                propriedade.endereco.endereco
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-outline-success" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Voltar</span>
        </button>
        <router-link
          v-if="propriedade.id"
          :to="{ name: 'PropriedadeEdit', params: { propriedadeId: propriedade.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-success">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Editar</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./propriedade-details.component.ts"></script>
