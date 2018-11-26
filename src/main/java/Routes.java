
import Classes.Address;
import Classes.Person;
import Classes.Search;
import Classes.Telephone;
import Interface.AddressDao;
import Interface.CountryDao;
import Interface.PersonDao;
import Interface.TelephoneDao;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import objectMapper.Mapper;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;


import static spark.Spark.*;

class main {

    public static void main(String[] args) {

        DB db = DB.getInstance(); //DATABASE CONNECTION

        Jdbi getDb = db.getJdbi();

         path("/person-database", () -> {

            path("/person", () -> {

                //SEARCHING FOR A PERSON IN THE DATABASE
                post("/search", "application/json", ((request, response) -> {
                    response.type("application/json");

                    try(Handle handle = db.getJdbi().open())
                    {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

                        Search search = mapper.readValue(request.body(), Search.class);
                        PersonDao dao = handle.attach(PersonDao.class);
                        return mapper.writeValueAsString(dao.searchPersons("%" + search.getInput() + "%"));
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        return "Something went wrong";
                    }
                }));

                //DISPLAYING A LIST OF PERSONS
                get("/list", "application/json", ((request, response) -> {
                    response.type("application/json");


                    try(Handle handle = db.getJdbi().open())
                    {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY); //hides null values

                        //selecting the list of persons
                        PersonDao dao = handle.attach(PersonDao.class);
                        return mapper.writeValueAsString(dao.getPersonList());

                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                        return "Something went wrong";
                    }


                }));

                //INSERTING A PERSON INTO THE DATABASE
                post("/add", "application/json", ((request, response) -> {
                    response.type("application/json");

                    try(Handle handle = db.getJdbi().open()) {

                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

                        Person person = mapper.readValue(request.body(), Person.class);
                        PersonDao dao = handle.attach(PersonDao.class);

                        if (dao.identifierTest(person)) {
                            return mapper.writeValueAsString(false);
                        } else {
                            dao.addPerson(person);
                            return mapper.writeValueAsString(true);
                        }
                   }
                    catch (Exception e) {
                        e.printStackTrace();
                        return "Something went wrong";
                    }
                }));


                post("/info", "application/json", (request, response) -> {
                    response.type("application/json");
                    try(Handle h = db.getJdbi().open() ) {

                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

                        Person person = mapper.readValue(request.body(), Person.class);
                        PersonDao dao = h.attach(PersonDao.class);
                        person = dao.getPersonInfo(person);
                        return mapper.writeValueAsString(person);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "Something went wrong";
                    }
                });

                put("/update", "application/json", ((request, response) -> {
                    response.type("application/json");

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

                    try(Handle handle = db.getJdbi().open()) {

                        Person person = mapper.readValue(request.body(), Person.class);
                        PersonDao dao = handle.attach(PersonDao.class);
                        dao.updatePerson(person);
                        return mapper.writeValueAsString(person);

                    }catch (Exception e) {
                        e.printStackTrace();
                        return "Something went wrong";
                    }
                }));


            });

            //TELEPHONE
            path("/telephone", () -> {

                get("/list", "application/json", ((request, response) -> {
                 response.type("application/json");

                 ObjectMapper mapper = new ObjectMapper();
                 mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                 mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

                 try(Handle handle = db.getJdbi().open()) {

                     TelephoneDao dao = handle.attach(TelephoneDao.class);
                     return mapper.writeValueAsString(dao.getStatus());
                 }
                 catch(Exception e)
                 {
                     System.out.println(e.getMessage());
                     return "";
                 }
             }));

                post("/add", "application/json", ((request, response) -> {
                    response.type("application.json");

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

                    try(Handle handle = db.getJdbi().open()) {

                        Telephone telephone = mapper.readValue(request.body(), Telephone.class);
                        TelephoneDao dao = handle.attach(TelephoneDao.class);


                        boolean result = dao.testTelephone(telephone);

                        System.out.println(result);

                        if (result) {
                            dao.addTel(telephone);
                        }

                        return mapper.writeValueAsString(result);
                    }  catch (Exception e) {
                        e.printStackTrace();
                        return "Something went wrong";
                    }
            }));

                post("/add-person-tel", "application/json", (request, response) -> {
                    response.type("application/json");

                    ObjectMapper mapper = Mapper.getInstance().getMapper();
                    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

                    try(Handle h = DB.getInstance().getJdbi().open()) {

                        Telephone telephone = mapper.readValue(request.body(), Telephone.class);
                        TelephoneDao dao = h.attach(TelephoneDao.class);

                        boolean result = dao.getPersonTelephoneResult(telephone);
                        System.out.println(dao.getPersonTelephoneResult(telephone));

                        if(result) {
                            boolean telephoneResult = dao.testTelephone(telephone);
                            if(telephoneResult) {
                                dao.addTel(telephone);
                                dao.addPersonTel(telephone);
                            } else {
                                dao.addPersonTel(telephone);
                            }
                        }
                        return mapper.writeValueAsString(result);

                    }catch (Exception e) {
                        e.printStackTrace();
                        return  "Something is wrong";
                    }
                });

                post("/info", "application/json", (((request, response) -> {
                response.type("application/json");

                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);


                try(Handle handle = db.getJdbi().open()) {

                    Telephone telephone = mapper.readValue(request.body(), Telephone.class);
                    TelephoneDao dao = handle.attach(TelephoneDao.class);
                    telephone = dao.getTelephoneInfo(telephone);
                    return mapper.writeValueAsString(telephone);

                }catch (Exception e) {
                    e.printStackTrace();
                    return "Something went wrong";
                }
            })));

                put("/update", "application/json", ((request, response) -> {
                 response.type("application/json");

                 ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

                 try(Handle handle = db.getJdbi().open()) {

                     Telephone telephone = mapper.readValue(request.body(), Telephone.class);
                     TelephoneDao dao = handle.attach(TelephoneDao.class);
                     dao.updateTel(telephone);
                     return mapper.writeValueAsString(telephone);

                 }catch (Exception e) {
                     e.printStackTrace();
                     return "Something went wrong";
                 }
             }));
            });

            //ADDRESS
            path("/address", () -> {

                get("/getAddressStatus", "application/json", ((request, response) -> {
                   response.type("application/json");

                   ObjectMapper mapper = new ObjectMapper();
                   mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                   mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

                   try(Handle handle = db.getJdbi().open()) {
//
//                       //marking an Address as current
                       AddressDao dao = handle.attach(AddressDao.class);
                       return mapper.writeValueAsString(dao.getStatus());
                   }
                   catch(Exception e){
                       System.out.println(e.getMessage());
                       return "Something went wrong";
                   }
               }));

//                post("/add", "application/json", ((request, response) -> {
//                    response.type("application.json");
//
//                    ObjectMapper mapper = objectMapper.Mapper.getInstance().getMapper();
//                    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//                    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
//
//                    try(Handle handle = db.getJdbi().open()) {
//
//                        Address address = mapper.readValue(request.body(), Address.class);
//                        AddressDao dao = handle.attach(AddressDao.class);
//
//                        boolean result = dao.testAddress(address);
//                        if(result) {
//                            dao.addAddress(address);
//                        }
//
//                        return mapper.writeValueAsString(result);
//
//                    }
//                    catch (Exception e) {
//                        e.printStackTrace();
//                        return "Something went wrong";
//                    }
//                }));

                post("/add-person-address", "application/json", (request, response) -> {
                    response.type("application/json");

                    ObjectMapper mapper = Mapper.getInstance().getMapper();
                    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

                    try(Handle h = DB.getInstance().getJdbi().open()) {

                        Address address = mapper.readValue(request.body(), Address.class);
                        AddressDao dao = h.attach(AddressDao.class);

                        boolean result = dao.getPersonAddressResult(address);
                        System.out.println(dao.getPersonAddressResult(address));

                        if (result) {
                            boolean addressResult = dao.testAddress(address);
                            if(addressResult) {
                                dao.addAddress(address);
                                dao.addPersonAddress(address);
                            } else {
                                dao.addPersonAddress(address);
                            }
                        }
                        return mapper.writeValueAsString(result);

                    }catch (Exception e) {
                        e.printStackTrace();
                        return  "Something is wrong";
                    }
                });

                post("/info", "application/json", ((request, response) -> {
                    response.type("application/json");

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);


                    try(Handle handle = db.getJdbi().open()) {

                        Address address = mapper.readValue(request.body(), Address.class);
                        AddressDao dao = handle.attach(AddressDao.class);
                        address = dao.getAddressInfo(address);
                        return mapper.writeValueAsString(address);

                    }catch (Exception e) {
                        e.printStackTrace();
                        return "Something went wrong";
                    }
                }));

                put("/update", "application/json", ((request, response) -> {
                    response.type("application/json");

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

                    try(Handle handle = db.getJdbi().open()) {

                        Address address = mapper.readValue(request.body(), Address.class);
                        AddressDao dao = handle.attach(AddressDao.class);
                        dao.updateAddress(address);
                        return mapper.writeValueAsString(address);

                    }catch (Exception e) {
                        e.printStackTrace();
                        return "Something went wrong";
                    }
                }));
            });

            //COUNTRY
             path("/country", () -> {
                get("/list", "application/json", (request, response) -> {
                    response.type("application/json");

                    try(Handle handle = getDb.open()) {
                        ObjectMapper mapper = objectMapper.Mapper.getInstance().getMapper();

                        CountryDao dao = handle.attach(CountryDao.class);
                        return mapper.writeValueAsString(dao.getCountryList());

                    }catch (Exception e) {
                        e.printStackTrace();
                        return "Something went wrong";
                    }
                });
             });
         });
    }
}
