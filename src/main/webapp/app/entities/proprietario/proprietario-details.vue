<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="proprietario">
        <h2 class="jh-entity-heading" data-cy="proprietarioDetailsHeading"><span>Proprietario</span> {{ proprietario.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Nome</span>
          </dt>
          <dd>
            <span>{{ proprietario.nome }}</span>
          </dd>
          <dt>
            <span>Telefone</span>
          </dt>
          <dd>
            <span>{{ proprietario.telefone }}</span>
          </dd>
          <dt>
            <span>E-mail</span>
          </dt>
          <dd>
            <span>{{ proprietario.email }}</span>
          </dd>
          <dt>
            <span>Enviar Laudo</span>
          </dt>
          <dd>
            <span>{{ proprietario.enviarLaudo }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-outline-success" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Voltar</span>
        </button>
        <router-link
          v-if="proprietario.id"
          :to="{ name: 'ProprietarioEdit', params: { proprietarioId: proprietario.id } }"
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

<script lang="ts" src="./proprietario-details.component.ts"></script>
