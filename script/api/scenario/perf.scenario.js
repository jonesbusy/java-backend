var copilot = require('api-copilot');
var _ = require('underscore');

// Filter to add authentication for requests
function addHeaders(requestOptions) {

	requestOptions.headers = {
		'Authorization': 'Bearer 1',
		'Accept': 'application/json',
		'Content-Type': 'application/json'
	};

	return requestOptions;
}

// Define scenario
var scenario = new copilot.Scenario({
	name: 'Perf',
	summary: 'Perf',
	baseUrl: "http://localhost:48080/api",
	defaultRequestOptions: {
		json: true
	}
});

scenario.configure({ requestPipeline: 50 });

scenario.step('List Events', function () {

	this.addRequestFilter(addHeaders);	

	return this.get({
		url: '/events?pageSize=1',
		expect: {statusCode: 200}
	});

});


//
// CREATE Events
//
var eventData = [
	
];

for(i = 0 ; i < 2000 ; i++)
	eventData.push([i, 'A detection : ' + i, 'detection', '2017-03-30T11:00:00.500+05:30', [{"foo" : "bar"}, {"bar" : "foor"}]]);

scenario.step('Create Events', function (response) {

	this.addRequestFilter(addHeaders);

	var requests = _.map(eventData, function (data) {
		return this.post({
			url: '/events',
			body: {
				uid: data[0],
				message: data[1],
				type: data[2],
				ts: data[3],
				properties: data[4],
				
			},
			expect: {statusCode: [201, 202]}
		});
	}, this);

	// run HTTP requests in parallel
	return this.all(requests);


});

// Export the scenario object
module.exports = scenario;
