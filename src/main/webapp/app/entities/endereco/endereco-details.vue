<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="endereco">
        <h2 class="jh-entity-heading" data-cy="enderecoDetailsHeading"><span>Endereço</span> {{ endereco.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Endereço</span>
          </dt>
          <dd>
            <span>{{ endereco.endereco }}</span>
          </dd>
          <dt>
            <span>CEP</span>
          </dt>
          <dd>
            <span>{{ endereco.cep }}</span>
          </dd>
          <dt>
            <span>Cidade</span>
          </dt>
          <dd>
            <span>{{ endereco.cidade }}</span>
          </dd>
          <dt>
            <span>Estado</span>
          </dt>
          <dd>
            <span>{{ endereco.estado }}</span>
          </dd>
          <dt>
            <span>Coordenadas GPS</span>
          </dt>
          <dd>
            <span>{{ endereco.coordenadasGps }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-outline-success" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Voltar</span>
        </button>
        <router-link v-if="endereco.id" :to="{ name: 'EnderecoEdit', params: { enderecoId: endereco.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-success">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Editar</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./endereco-details.component.ts"></script>
