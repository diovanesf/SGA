<template>
  <div class="home row">
    <div class="col-md-3">
      <span class="hipster img-fluid rounded"></span>
    </div>
    <div class="col-md-9">
      <h1 class="display-4">Bem vindo!</h1>
      <p class="lead"><strong>Sistema de Gerenciamento de Amostras</strong></p>

      <div>
        <div class="alert alert-success" v-if="authenticated">
          <span v-if="username">Você está logado com o usuário "{{ username }}"</span>
        </div>

        
        <div class="alert alert-success" v-if="!authenticated">
          <span>Você precisa fazer </span>
          <a class="alert-link" v-on:click="openLogin()"><strong>login</strong></a
          ><span
            > para acessar o sistema.</span
          >
        </div>
        <!--        <div class="alert alert-success" v-if="!authenticated">-->
        <!--          <span>Não possui uma conta ainda?</span>&nbsp;-->
        <!--          <router-link class="alert-link" to="/register">Crie uma nova conta</router-link>-->
        <!--        </div>-->
        
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>
