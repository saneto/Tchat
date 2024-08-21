isPropertyEmpty(property: any): boolean {
  if (property == null || property === '') return true;  // Null, undefined, or empty string
  if (Array.isArray(property) && property.length === 0) return true;  // Empty array
  if (typeof property === 'object' && Object.keys(property).length === 0) return true;  // Empty object
  return false;
}

checkModelEmpty(model: MyModel): { allEmpty: boolean; emptyProperties: string[] } {
  const emptyProperties = [];

  for (const [key, value] of Object.entries(model)) {
    if (this.isPropertyEmpty(value)) {
      emptyProperties.push(key);
    }
  }

  return {
    allEmpty: emptyProperties.length === Object.keys(model).length,
    emptyProperties
  };
}

// Usage
const model: MyModel = {
  name: '',
  age: null,
  address: '',
  skills: []
};

const result = this.checkModelEmpty(model);

console.log(result.allEmpty);  // true if all properties are empty
console.log(result.emptyProperties);  // ["name", "age", "address", "skills"]