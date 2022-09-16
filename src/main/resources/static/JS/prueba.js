let array1=[1,2,10,6,9,11]
let array2=[
    { id: 4,
      balance: 138.2,
      number: "VIN004"},
      { id: 5,
        balance: 48.2,
        number: "VIN004"},
        { id: 9,
          balance: 108.2,
          number: "VIN004"}
  ]

console.log(array1.sort((a,b)=>{
  return a-b
}))

console.log(array1.sort((a,b)=>{
  return b-a
}))

console.log(array2.sort((a,b)=>{
  return b.id - a.id
}));