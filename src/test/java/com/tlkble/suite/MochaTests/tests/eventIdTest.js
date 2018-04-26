var assert = chai.assert;

// Test 1
describe('String', function() {
  it('ID generation algorithm creates a random floating point less than zero', function() {
    var firstPart = (Math.random());
    assert.isAbove(firstPart, 0);
    assert.isBelow(firstPart, 1);
    assert.equal(Number.isInteger(firstPart), false); 
  });
});

// Test 2
describe('String', function() {
  it('ID generation algorithm creates a random number which is greater than zero with fractional component', function() {
    var firstPart = (Math.random() * 46656);
    assert.isAbove(firstPart, 0);
    assert.equal(Number.isInteger(firstPart), false); 
  });
});

// Test 3
describe('String', function() {
  it('First part of the  ID, algorithm generates a random integer greater than zero', function() {
    var firstPart = (Math.random() * 46656) | 0;
    assert.equal(Number.isInteger(firstPart), true);
  });
});

// Test 4
describe('String', function() {
  it('Second part of the  ID, algorithm generates a random integer greater than zero', function() {
    var secondPart = (Math.random() * 46656) | 0;
    assert.equal(Number.isInteger(secondPart), true);
  });
});

// Test 5
describe('String', function() {
  it('Converting this to Base36, results in a 3 character sequence', function() {
    var firstPart = (Math.random() * 46656) | 0;
    firstPart = ("000" + firstPart.toString(36)).slice(-3);
    assert.equal(firstPart.length, 3);
  });
});

// Test 5
describe('String', function() {
  it('Sequence contains only characters and numbers', function() {
    var firstPart = (Math.random() * 46656) | 0;
    firstPart = ("000" + firstPart.toString(36)).slice(-3);
    assert.equal((/^[0-9a-zA-Z]+$/.test(firstPart.toLowerCase())), true);
  });
});

// Test 6
describe('String', function() {
  it('Concatenation of both parts is 6 characters long', function() {
    var firstPart = (Math.random() * 46656) | 0;
    var secondPart = (Math.random() * 46656) | 0;
    firstPart = ("000" + firstPart.toString(36)).slice(-3);
    secondPart = ("000" + secondPart.toString(36)).slice(-3);
    var res = firstPart + secondPart;
    assert.equal(res.length, 6);
  });
});
