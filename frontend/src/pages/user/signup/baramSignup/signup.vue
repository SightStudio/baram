<!--
    회원 가입 페이지

    @author Dong-Min Seol
    @since  2019.05.16
-->
<template>
  <f7-page>

    <logo></logo>

    <f7-list>
      <f7-list-input
				label="아이디"
				type="text"
				placeholder="Your name"
				info="Default validation"
				required
				validate
				clear-button
			> <f7-icon icon="demo-list-icon" slot="media"></f7-icon>
			</f7-list-input>

			<f7-list-input
				label="Fruit"
				type="text"
				placeholder="Type 'apple' or 'banana'"
				required
				validate
				pattern="apple|banana"
				clear-button
			> <f7-icon icon="demo-list-icon" slot="media"></f7-icon>
			  <span slot="info">Pattern validation (<b>apple|banana</b>)</span>
			</f7-list-input>

			<f7-list-input
				label="E-mail"
				type="email"
				placeholder="Your e-mail"
				info="Default e-mail validation"
				required
				validate
				clear-button
			> <f7-icon icon="demo-list-icon" slot="media"></f7-icon>
			</f7-list-input>

			<f7-list-input
				label="URL"
				type="url"
				placeholder="Your URL"
				info="Default URL validation"
				required
				validate
				clear-button
			> <f7-icon icon="demo-list-icon" slot="media"></f7-icon>
			</f7-list-input>

			<f7-list-input
				label="Number"
				type="text"
				placeholder="Enter number"
				info="With custom error message"
				error-message="Only numbers please!"
				required
				validate
				pattern="[0-9]*"
				clear-button
			> <f7-icon icon="demo-list-icon" slot="media"></f7-icon>
			</f7-list-input>
      
    </f7-list>

    <f7-block strong no-hairlines-ios>
      <f7-row>
        <f7-col>
          <f7-button fill raised>회원 가입하기</f7-button>
        </f7-col>
      </f7-row>
    </f7-block>
  </f7-page>
</template>

<script>
import logo from '{COMPONENT}/logo/logo.vue'
export default {
  components : {
    logo
  },
}
</script>
<style scoped>
.logo { width: 100%; height: 200px; text-align: center; font-size: 0; margin-top: 30px; }
.logo img{ width: 100%; height:200px; display: inline-block; }

</style>