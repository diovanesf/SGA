<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="user">
        <h2 class="jh-entity-heading">
          <span>Usuário</span> [<strong>{{ user.login }}</strong
          >]
        </h2>
        <dl class="row jh-entity-details">
          <dt><span>Login</span></dt>
          <dd>
            <span>{{ user.login }}</span>
            <b-badge variant="success" v-if="user.activated">Ativado</b-badge>
            <b-badge variant="danger" v-if="!user.activated">Desativado</b-badge>
          </dd>
          <dt><span>Nome</span></dt>
          <dd>{{ user.firstName }}</dd>
          <dt><span>Sobrenome</span></dt>
          <dd>{{ user.lastName }}</dd>
          <dt><span>E-mail</span></dt>
          <dd>{{ user.email }}</dd>
          <dt><span>Criado por</span></dt>
          <dd>{{ user.createdBy }}</dd>
          <dt><span>Data de criação</span></dt>
          <dd>{{ user.createdDate | formatDate }}</dd>
          <dt><span>Última modificação por</span></dt>
          <dd>{{ user.lastModifiedBy }}</dd>
          <dt><span>Data da última modificação</span></dt>
          <dd>{{ user.lastModifiedDate | formatDate }}</dd>
          <dt><span>Perfis</span></dt>
          <dd>
            <ul class="list-unstyled">
              <li v-for="authority of user.authorities" :key="authority">
                <b-badge variant="info">{{ authority }}</b-badge>
              </li>
            </ul>
          </dd>
        </dl>
        <router-link custom v-slot="{ navigate }" :to="{ name: 'JhiUser' }">
          <button @click="navigate" class="btn btn-success">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Voltar</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./user-management-view.component.ts"></script>
