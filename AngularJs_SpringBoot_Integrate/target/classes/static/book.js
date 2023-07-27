var app = angular.module('myApp', []);

app.controller('booksCtrl', function($scope, $http) {
	$scope.books = [];
	$scope.newBook = {};
	$scope.selectedBook = {};
	$scope.isEditing = false;

	$http.get('/books').then(function(response) {
		$scope.books = response.data;
	});

	$scope.submit = function() {
		$http.post('/books', $scope.newBook).then(function(response) {
			$scope.books.push(response.data);
			$scope.newBook = {};
		});
	};

	$scope.edit = function(book) {
		$scope.selectedBook = angular.copy(book);
		$scope.isEditing = true;
	};

	$scope.update = function() {
		$http.put('/books/' + $scope.selectedBook.id, $scope.selectedBook).then(function(response) {
			var index = $scope.books.findIndex(function(book) {
				return book.id === response.data.id;
			});
			$scope.books[index] = response.data;
			$scope.isEditing = false;
		});
	};

	$scope.delete = function(book) {
		$http.delete('/books/' + book.id).then(function() {
			var index = $scope.books.indexOf(book);
			$scope.books.splice(index, 1);
		});
	};

	$scope.cancel = function() {
		$scope.selectedBook = {};
		$scope.isEditing = false;
	};
});