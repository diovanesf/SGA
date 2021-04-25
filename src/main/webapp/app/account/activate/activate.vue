<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-md-8">
        <h1>Activation</h1>
        <div class="alert alert-success" v-if="success">
          <span><strong>Sua conta foi ativada!.</strong> Por favor </span>
          <a class="alert-link" v-on:click="openLogin">faça login</a>.
        </div>
        <div class="alert alert-danger" v-if="error">
          <strong>Seu usuário não foi ativado.</strong> Por favor, faça seu registro.
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./activate.component.ts"></script>
