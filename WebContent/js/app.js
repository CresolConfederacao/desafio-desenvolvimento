var serviceURIBase = 'http://localhost:8080/desafio-desenvolvimento/api/';

function simulacao($scope, $http) {
	$scope.register = {nome: "", cpf: "", email: "", valor: "", qtdParcelas: "" };
	$scope.simular = function() {
		$http.post(serviceURIBase + "emprestimo/simular", $scope.register, { headers: {'Content-Type': 'application/json'} })
	    .success(function (response) {
	    	$scope.contrato = response;
	    });
	};
}


angular.module('Index', [])
.controller('simulacao', simulacao);